package net.intelryzen.ultimatequest.block.entity;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.UQBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UQBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE,
            UltimateQuestMod.MODID);

    public static final Supplier<BlockEntityType<ProcessorBlockEntity>> PROCESSOR_BE = BLOCK_ENTITIES
            .register("processor", () -> BlockEntityType.Builder.of(ProcessorBlockEntity::new, UQBlocks.PROCESSOR.get()).build(null));

    public static final Supplier<BlockEntityType<RefinerBlockEntity>> REFINER_BE = BLOCK_ENTITIES
            .register("refiner", () -> BlockEntityType.Builder.of(RefinerBlockEntity::new, UQBlocks.REFINER.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
