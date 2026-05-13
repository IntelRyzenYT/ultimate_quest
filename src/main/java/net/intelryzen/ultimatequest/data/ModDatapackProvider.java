package net.intelryzen.ultimatequest.data;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.biome.UQBiomes;
import net.intelryzen.ultimatequest.dimension.UQDimensionTypes;
import net.intelryzen.ultimatequest.dimension.UQLevelStems;
import net.intelryzen.ultimatequest.dimension.UQNoiseSettings;
import net.intelryzen.ultimatequest.worldgen.UQBiomeModifiers;
import net.intelryzen.ultimatequest.worldgen.UQConfiguredFeatures;
import net.intelryzen.ultimatequest.worldgen.UQPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.security.Provider;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, UQBiomes::bootstrap)
            .add(Registries.DIMENSION_TYPE, UQDimensionTypes::bootstrap)
            .add(Registries.NOISE_SETTINGS, UQNoiseSettings::bootstrap)
            .add(Registries.LEVEL_STEM, UQLevelStems::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, UQConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, UQPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UQBiomeModifiers::bootstrap);


    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(UltimateQuestMod.MODID));
    }
}
