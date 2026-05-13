package net.intelryzen.ultimatequest.util;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.LevelStem;

public class UQTags {
    public static class Items {

        public static final TagKey<Item> CAN_BREAK_ULTRA_ORE = createTag("can_break_ultra_ore");
        public static final TagKey<Item> CAN_BREAK_DM_ORE = createTag("can_break_dm_ore");


        public static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
        }
    }

    public static class Blocks {
        public static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> IS_VOID_BIOME = createTag("is_void_biome");

        public static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
        }
    }

    public static class Dimensions {
        public static final TagKey<Level> IS_VOID_DIMENSION = createTag("is_void_dimension");

        public static TagKey<Level> createTag(String name) {
            return TagKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
        }
    }

    public static class LevelStems {
        public static final TagKey<LevelStem> IS_VOID_LEVEL_STEM = createTag("is_void_level_stem");

        public static TagKey<LevelStem> createTag(String name) {
            return TagKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, name));
        }
    }
}

