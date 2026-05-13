package net.intelryzen.ultimatequest.data;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.item.UQItems;
import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, UltimateQuestMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(UQTags.Items.CAN_BREAK_ULTRA_ORE)
                .add(Items.NETHERITE_PICKAXE);

        tag(UQTags.Items.CAN_BREAK_DM_ORE)
                .add(UQItems.BEDROCK_BREAKER.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(UQItems.ULTRA_HELMET.get())
                .add(UQItems.ULTRA_CHESTPLATE.get())
                .add(UQItems.ULTRA_LEGGINGS.get())
                .add(UQItems.ULTRA_BOOTS.get());
    }
}
