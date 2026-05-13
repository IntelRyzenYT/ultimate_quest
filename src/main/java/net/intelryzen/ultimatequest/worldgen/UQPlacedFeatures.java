package net.intelryzen.ultimatequest.worldgen;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class UQPlacedFeatures {
    public static final ResourceKey<PlacedFeature> PLACED_ULTRA_DUST_ORE_KEY = registerKey("ultra_dust_ore_placed");

    public static final ResourceKey<PlacedFeature> DM_ORE_KEY = registerKey("dark_matter_ore_key");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, PLACED_ULTRA_DUST_ORE_KEY, configuredFeatures.getOrThrow(UQConfiguredFeatures.ULTRA_DUST_ORE_KEY),
                UQOrePlacements.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(15), VerticalAnchor.absolute(40))));

        register(context, DM_ORE_KEY, configuredFeatures.getOrThrow(UQConfiguredFeatures.DARK_MATTER_ORE_KEY),
                UQOrePlacements.commonOrePlacement(30,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(53), VerticalAnchor.absolute(1600))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
