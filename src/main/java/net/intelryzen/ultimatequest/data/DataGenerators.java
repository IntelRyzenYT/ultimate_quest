package net.intelryzen.ultimatequest.data;
import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
// 1. Notice the 's' at the end of ParamSets here!
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = UltimateQuestMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent dataEvent) {
        DataGenerator generator = dataEvent.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = dataEvent.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = dataEvent.getLookupProvider();

        generator.addProvider(dataEvent.includeServer(), new LootTableProvider(
                output,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider
        ));

        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(output, lookupProvider, helper);

        generator.addProvider(dataEvent.includeServer(), blockTagsProvider);
        generator.addProvider(dataEvent.includeServer(), new ModItemTagProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));



        generator.addProvider(dataEvent.includeClient(), new ModItemModelProvider(output, helper));
        generator.addProvider(dataEvent.includeClient(), new ModBlockStateProvider(output, helper));

        ModDatapackProvider datapackProvider = new ModDatapackProvider(output, lookupProvider);

        generator.addProvider(dataEvent.includeServer(), datapackProvider);
     //   generator.addProvider(dataEvent.includeServer(), new ModDimensionTagProvider(output, lookupProvider, helper));
        generator.addProvider(dataEvent.includeServer(), new ModBiomeTagsProvider(output, datapackProvider.getRegistryProvider(), helper));
        generator.addProvider(dataEvent.includeServer(), new ModRecipeProvider(output, lookupProvider));

    }
}