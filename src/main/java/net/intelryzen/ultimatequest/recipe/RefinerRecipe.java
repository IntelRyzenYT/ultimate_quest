package net.intelryzen.ultimatequest.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import java.sql.Ref;

public record RefinerRecipe(Ingredient inputItem, FluidStack inputFluid, ItemStack output) implements Recipe<RefinerRecipeInput> {
    @Override
    public boolean matches(RefinerRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        boolean itemMatches = inputItem.test(input.getItem(0));

        boolean fluidTypeMatches = FluidStack.isSameFluid(input.fluid(), this.inputFluid);

        boolean hasEnoughFluid = input.fluid().getAmount() >= this.inputFluid.getAmount();

        return itemMatches && fluidTypeMatches && hasEnoughFluid;
    }

    @Override
    public ItemStack assemble(RefinerRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return UQRecipes.REFINER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UQRecipes.REFINER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<RefinerRecipe> {
        public static final MapCodec<RefinerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(RefinerRecipe::inputItem),
                FluidStack.CODEC.fieldOf("fluid").forGetter(RefinerRecipe::inputFluid),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(RefinerRecipe::output)
        ).apply(inst, RefinerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RefinerRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, RefinerRecipe::inputItem,
                FluidStack.STREAM_CODEC, RefinerRecipe::inputFluid,
                ItemStack.STREAM_CODEC, RefinerRecipe::output,
                RefinerRecipe::new
        );

        @Override
        public MapCodec<RefinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RefinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
