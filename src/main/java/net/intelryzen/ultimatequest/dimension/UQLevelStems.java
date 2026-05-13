package net.intelryzen.ultimatequest.dimension;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.biome.UQBiomes;
import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;

import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;


public class UQLevelStems {
    public static final ResourceKey<LevelStem> VOID_LEVEL_STEM = ResourceKey.create(
            Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "void")
    );

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        Holder<Biome> voidBiome = context.lookup(Registries.BIOME).getOrThrow(UQBiomes.VOID_BIOME);
        Holder<NoiseGeneratorSettings> settings = context.lookup(Registries.NOISE_SETTINGS).getOrThrow(UQNoiseSettings.VOID_SETTINGS);
        Holder<DimensionType> voidType = context.lookup(Registries.DIMENSION_TYPE).getOrThrow(UQDimensionTypes.VOID_TYPE);
        context.register(VOID_LEVEL_STEM, new LevelStem(
                voidType,
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(voidBiome),
                        settings
                )

        ));
    }

}
