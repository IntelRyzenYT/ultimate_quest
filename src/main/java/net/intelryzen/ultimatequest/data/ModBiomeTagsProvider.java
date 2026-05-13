package net.intelryzen.ultimatequest.data;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.biome.UQBiomes;
import net.intelryzen.ultimatequest.util.UQTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, UltimateQuestMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(UQTags.Biomes.IS_VOID_BIOME)
                .add(UQBiomes.VOID_BIOME);
    }
}
