package net.intelryzen.ultimatequest.data;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.UQBlocks;
import net.intelryzen.ultimatequest.fluid.UQFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UltimateQuestMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(UQBlocks.ULTRA_DUST_ORE);
        blockWithItem(UQBlocks.DARK_MATTER_ORE);

        simpleFluid(UQBlocks.CATALYST_BLOCK);

        processorBlockWithItem();
        refinerBlockWithItem();


    }

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void processorBlockWithItem() {
        ResourceLocation other_unpowered = modLoc("block/processor_side_unpowered");
        ResourceLocation other_powered = modLoc("block/processor_side_powered");
        ResourceLocation top = modLoc("block/processor_top");
        ResourceLocation bottom = modLoc("block/processor_bottom");

        BlockModelBuilder off = models().cubeBottomTop("processor_off", other_unpowered, bottom, top);
        BlockModelBuilder on = models().cubeBottomTop("processor_on", other_powered, bottom, top);

        getVariantBuilder(UQBlocks.PROCESSOR.get()).forAllStates(blockState -> {
            boolean isPowered = blockState.getValue(BlockStateProperties.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(isPowered ? on : off)
                    .build();
        });

        simpleBlockItem(UQBlocks.PROCESSOR.get(), off);
    }

    private void refinerBlockWithItem() {
        ResourceLocation side = modLoc("block/refiner_side");
        ResourceLocation top = modLoc("block/refiner_top");
        ResourceLocation bottom = modLoc("block/refiner_bottom");

        BlockModelBuilder model = models().cubeBottomTop("refiner", side, bottom, top);

        getVariantBuilder(UQBlocks.REFINER.get()).forAllStates(blockState -> {
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        simpleBlockItem(UQBlocks.REFINER.get(), model);
    }

    private void simpleFluid(DeferredBlock<LiquidBlock> fluidBlock) {

        BlockModelBuilder model = models().getBuilder(fluidBlock.getId().getPath())
                .parent(models().getExistingFile(mcLoc("block/thin_block")));

        getVariantBuilder(fluidBlock.get()).forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(model)
                        .build());
    }

}
