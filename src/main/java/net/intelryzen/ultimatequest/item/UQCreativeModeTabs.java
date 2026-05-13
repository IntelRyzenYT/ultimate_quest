package net.intelryzen.ultimatequest.item;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.block.UQBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UQCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UltimateQuestMod.MODID);

    public static final Supplier<CreativeModeTab> ULTIMATE_QUEST_TAB =
            CREATIVE_MODE_TABS.register("ultimate_quest_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(UQItems.ULTRA_DUST.get()))
                            .title(Component.translatable("itemGroup.ultimatequest.ultimate_quest_tab"))
                            .displayItems((a, output) -> {
                                // items
                                output.accept(UQItems.DARK_MATTER.get());
                                output.accept(UQItems.ULTRA_BOOTS.get());
                                output.accept(UQItems.ULTRA_HELMET .get());
                                output.accept(UQItems.ULTRA_LEGGINGS.get());
                                output.accept(UQItems.ULTRA_CHESTPLATE.get());
                                output.accept(UQItems.BEDROCK_BREAKER.get());
                                output.accept(UQItems.PIG_DESTROYER.get());
                                output.accept(UQItems.ULTRA_DUST.get());
                                output.accept(UQItems.PROCESSED_ULTRA_DUST.get());
                                output.accept(UQItems.OBSIDIAN_PROCESSED_ULTRA_DUST.get());
                                output.accept(UQItems.ULTRA_INGOT.get());

                                //blocks

                                output.accept(UQBlocks.DARK_MATTER_ORE);
                                output.accept(UQBlocks.ULTRA_DUST_ORE);
                                output.accept(UQBlocks.PROCESSOR);
                            })
                            .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
