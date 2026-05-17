package net.intelryzen.ultimatequest.fluid;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.UQBlocks;
import net.intelryzen.ultimatequest.item.UQItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class UQFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, UltimateQuestMod.MODID);

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, UltimateQuestMod.MODID);

    public static final DeferredHolder<FluidType, FluidType> CATALYST_TYPE =
            FLUID_TYPES.register("catalyst_type", () ->
                    new FluidType(FluidType.Properties.create()
                            .lightLevel(10)
                            .density(1500)
                            .viscosity(2000)) {
                        @Override
                        public void initializeClient(java.util.function.Consumer<IClientFluidTypeExtensions> consumer) {
                            consumer.accept(new IClientFluidTypeExtensions() {
                                private static final ResourceLocation STILL =
                                        ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "block/catalyst_still");
                                private static final ResourceLocation FLOWING =
                                        ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "block/catalyst_flowing");

                                @Override
                                public ResourceLocation getStillTexture() {
                                    return STILL;
                                }

                                @Override
                                public ResourceLocation getFlowingTexture() {
                                    return FLOWING;
                                }
                            });
                        }
                    });

    private static final Supplier<BaseFlowingFluid.Properties> FLUID_PROPERTIES = () -> new BaseFlowingFluid.Properties(
            CATALYST_TYPE,
            UQFluids.CATALYST_SOURCE,
            UQFluids.CATALYST_FLOWING
    ).bucket(UQItems.CATALYST_BUCKET).block(UQBlocks.CATALYST_BLOCK);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> CATALYST_SOURCE =
            FLUIDS.register("catalyst_source", () -> new BaseFlowingFluid.Source(FLUID_PROPERTIES.get()));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> CATALYST_FLOWING =
            FLUIDS.register("catalyst_flowing", () -> new BaseFlowingFluid.Flowing(FLUID_PROPERTIES.get()));


    public static void register(IEventBus bus) {
        FLUID_TYPES.register(bus);
        FLUIDS.register(bus);
    }
}
