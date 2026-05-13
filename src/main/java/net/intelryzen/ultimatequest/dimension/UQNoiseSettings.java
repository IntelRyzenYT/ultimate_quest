package net.intelryzen.ultimatequest.dimension;

import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class UQNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> VOID_SETTINGS = ResourceKey.create(
            Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath("ultimatequest", "void_settings"));

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(VOID_SETTINGS, voidSettings(context));
    }

    private static NoiseGeneratorSettings voidSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        var densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);

        var slopedCheese = densityFunctions.getOrThrow(
                ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace("overworld/sloped_cheese")));

        DensityFunction finalDensity = DensityFunctions.interpolated(
                DensityFunctions.blendDensity(new DensityFunctions.HolderHolder(slopedCheese))
        );

        NoiseRouter router = new NoiseRouter(
                DensityFunctions.zero(), // barrier
                DensityFunctions.zero(), // fluid_level_floodedness
                DensityFunctions.zero(), // fluid_level_spread
                DensityFunctions.zero(), // lava
                DensityFunctions.zero(), // temperature
                DensityFunctions.zero(), // vegetation
                DensityFunctions.zero(), // continents
                DensityFunctions.zero(), // erosion
                DensityFunctions.zero(), // depth
                DensityFunctions.zero(), // ridges
                DensityFunctions.zero(), // initial_density_without_jaggedness
                finalDensity,           // <--- MOVE IT HERE (12th Argument)
                DensityFunctions.zero(), // vein_toggle
                DensityFunctions.zero(), // vein_ridged
                DensityFunctions.zero()  // vein_gap
        );

        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                )
        );

        return new NoiseGeneratorSettings(
                new NoiseSettings(0, 1600, 1, 2),
                Blocks.BEDROCK.defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                router,
                surfaceRule,
                List.of(),
                0,
                false,
                true,
                true,
                false
        );
    }



}
