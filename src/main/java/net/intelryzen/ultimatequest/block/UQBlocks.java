package net.intelryzen.ultimatequest.block;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.custom.ProcessorBlock;
import net.intelryzen.ultimatequest.block.custom.RefinerBlock;
import net.intelryzen.ultimatequest.fluid.UQFluids;
import net.intelryzen.ultimatequest.item.UQItems;
import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.function.Supplier;


public class UQBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateQuestMod.MODID);

    public static final DeferredBlock<Block> ULTRA_DUST_ORE = registerBlock("ultra_dust_ore", () -> new Block(Properties.of().requiresCorrectToolForDrops().strength(5.0F)) {
        @Override
        public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
            return player.getItemInHand(InteractionHand.MAIN_HAND).is(UQTags.Items.CAN_BREAK_ULTRA_ORE);
        }

        @Override
        protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
            return (player.getItemInHand(InteractionHand.MAIN_HAND).is(UQTags.Items.CAN_BREAK_ULTRA_ORE)) ?
                    super.getDestroyProgress(state, player, level, pos) : 0.002F;
        }
    });

    public static final DeferredBlock<Block> DARK_MATTER_ORE = registerBlock("dark_matter_ore", () -> new Block(Properties.of().requiresCorrectToolForDrops().strength(5.0F)) {
        @Override
        public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
            return player.getItemInHand(InteractionHand.MAIN_HAND).is(UQTags.Items.CAN_BREAK_DM_ORE);
        }

        @Override
        protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
            return (player.getItemInHand(InteractionHand.MAIN_HAND).is(UQTags.Items.CAN_BREAK_DM_ORE)) ?
                    super.getDestroyProgress(state, player, level, pos) : -1.0F;
        }
    });

    public static final DeferredBlock<LiquidBlock> CATALYST_BLOCK = BLOCKS.register("catalyst",
            () -> new LiquidBlock(UQFluids.CATALYST_FLOWING.get(), Properties.ofFullCopy(Blocks.WATER)
                    .noLootTable()));

    public static final DeferredBlock<Block> PROCESSOR = registerBlock("processor",
            () -> new ProcessorBlock(Properties.of()));

    public static final DeferredBlock<Block> REFINER = registerBlock("refiner",
            () -> new RefinerBlock(Properties.of()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        UQItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
