package net.intelryzen.ultimatequest.worldgen;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.UQBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.apache.commons.codec.language.bm.Rule;

import java.util.List;

public class UQConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ULTRA_DUST_ORE_KEY = registerKey("ultra_dust_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_MATTER_ORE_KEY = registerKey("dark_matter_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);
        RuleTest bedrockReplaceables = new BlockMatchTest(Blocks.BEDROCK);

        List<OreConfiguration.TargetBlockState> endUltraDustOres = List.of(
                OreConfiguration.target(endReplaceables, UQBlocks.ULTRA_DUST_ORE.get().defaultBlockState())
        );

        register(context, ULTRA_DUST_ORE_KEY, Feature.ORE, new OreConfiguration(endUltraDustOres, 5));

        List<OreConfiguration.TargetBlockState> bedrockDarkMatterOres = List.of(
                OreConfiguration.target(bedrockReplaceables, UQBlocks.DARK_MATTER_ORE.get().defaultBlockState())
        );

        register(context, DARK_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(bedrockDarkMatterOres, 5));


    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
