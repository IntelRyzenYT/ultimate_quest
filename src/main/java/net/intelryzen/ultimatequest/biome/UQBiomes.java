package net.intelryzen.ultimatequest.biome;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class UQBiomes {
    public static final ResourceKey<Biome> VOID_BIOME = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "void"));


    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(VOID_BIOME, voidBiome(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }

    public static Biome voidBiome(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.creatureGenerationProbability(0.0f);

        BiomeGenerationSettings.Builder genBuilder = new BiomeGenerationSettings.Builder(features, carvers);


        BiomeSpecialEffects.Builder effectsBuilder = new BiomeSpecialEffects.Builder()
                .fogColor(10592673)
                .skyColor(0)
                .waterColor(4475477)
                .waterFogColor(5592424)
                .foliageColorOverride(1842204)
                .grassColorOverride(4868682);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(2.0f)
                .downfall(0f)
                .specialEffects(effectsBuilder.build())
                .generationSettings(genBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .build();
    }
}
