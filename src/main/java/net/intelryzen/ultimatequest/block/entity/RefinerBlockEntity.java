package net.intelryzen.ultimatequest.block.entity;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.fluid.UQFluids;
import net.intelryzen.ultimatequest.recipe.RefinerRecipe;
import net.intelryzen.ultimatequest.recipe.RefinerRecipeInput;
import net.intelryzen.ultimatequest.recipe.UQRecipes;
import net.intelryzen.ultimatequest.screen.custom.RefinerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RefinerBlockEntity extends BlockEntity implements MenuProvider {
    public final FluidTank fluidTank = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 2 -> stack.getCapability(Capabilities.FluidHandler.ITEM) != null;
                default -> true;
            };
        }
    };

    public void setFluidStack(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.fluidTank.getFluid();
    }

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FLUID_HANDLER_ITEM_SLOT = 2;

    protected final ContainerData data;
    private int progress = 0;
    private int max_progress = 72;

    public RefinerBlockEntity(BlockPos pos, BlockState blockState) {
        super(UQBlockEntities.REFINER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RefinerBlockEntity.this.progress;
                    case 1 -> RefinerBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RefinerBlockEntity.this.progress = value;
                    case 1 -> RefinerBlockEntity.this.max_progress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        processBucketSlot();
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<RefinerRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return;

        RefinerRecipe recipe = recipeOpt.get().value();
        ItemStack recipeOutput = recipe.output();
        ItemStack currentOutput = itemHandler.getStackInSlot(OUTPUT_SLOT);

        int fluidAmountToDrain = recipe.inputFluid().getAmount();
        this.fluidTank.drain(fluidAmountToDrain, IFluidHandler.FluidAction.EXECUTE);

        this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);

        if (currentOutput.isEmpty()) {
            itemHandler.setStackInSlot(OUTPUT_SLOT, recipeOutput.copy());
        } else {
            currentOutput.grow(recipeOutput.getCount());
        }
    }

    private void resetProgress() {
        progress = 0;
        max_progress = 72;
    }

    private boolean hasCraftingFinished() {
        return progress >= max_progress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<RefinerRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            return canInsertIntoOutputSlot(recipe.get().value().output());
        } else {
            return false;
        }
    }

    private Optional<RecipeHolder<RefinerRecipe>> getCurrentRecipe() {
        ItemStack itemInSlot = itemHandler.getStackInSlot(INPUT_SLOT);
        FluidStack fluidInTank = fluidTank.getFluid();
        return this.level.getRecipeManager().getRecipeFor(UQRecipes.REFINER_TYPE.get(), new RefinerRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT), fluidTank.getFluid()), level);
    }

    private boolean canInsertIntoOutputSlot(ItemStack output) {
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        ItemStack currentOutput = itemHandler.getStackInSlot(OUTPUT_SLOT);
        return currentOutput.isEmpty() || ItemStack.isSameItemSameComponents(currentOutput, output);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    private void processBucketSlot() {
        ItemStack stack = itemHandler.getStackInSlot(FLUID_HANDLER_ITEM_SLOT);
        if (stack.isEmpty()) return;

        IFluidHandlerItem itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);

        if (itemFluidHandler != null) {
            FluidStack transferred = FluidUtil.tryFluidTransfer(
                    this.fluidTank,
                    itemFluidHandler,
                    1000,
                    true
            );

            if (!transferred.isEmpty()) {
                itemHandler.setStackInSlot(FLUID_HANDLER_ITEM_SLOT, itemFluidHandler.getContainer());
            }
        }
    }





    @Override
    public Component getDisplayName() {
        return Component.translatable("block.ultimatequest.refiner");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new RefinerMenu(containerId, playerInventory, this, data);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.itemHandler.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        tag.putInt("max_progress", this.max_progress);

        CompoundTag fluidTag = new CompoundTag();
        this.fluidTank.writeToNBT(registries, fluidTag);
        tag.put("fluidTank", fluidTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.max_progress = tag.getInt("max_progress");
        this.fluidTank.readFromNBT(registries, tag.getCompound("fluidTank"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
