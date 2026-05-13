package net.intelryzen.ultimatequest.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class UQDimensions {
    public static final ResourceKey<Level> VOID_DIMENSION_LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("ultimatequest", "void")
    );


}
