package net.intelryzen.ultimatequest.dimension;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class UQDimensionTypes {
    public static final ResourceKey<DimensionType> VOID_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "void_type")
    );

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(VOID_TYPE, new DimensionType(
                OptionalLong.of(18000L),
                false,
                true,
                false,
                false,
                1.0D,
                false,
                false,
                0,
                1600,
                1600,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.NETHER_EFFECTS,
                1.0F,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)

        ));
    }
}
