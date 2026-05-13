package net.intelryzen.ultimatequest.worldgen;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class UQBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ULTRA_ORE = registerKey("add_ultra_ore");

    public static final ResourceKey<BiomeModifier> ADD_DM_ORE = registerKey("add_dm_ore");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ULTRA_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(UQPlacedFeatures.PLACED_ULTRA_DUST_ORE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
                ));

       context.register(ADD_DM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(UQTags.Biomes.IS_VOID_BIOME),
                HolderSet.direct(placedFeatures.getOrThrow(UQPlacedFeatures.DM_ORE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
    }
}