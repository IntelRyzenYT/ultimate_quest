package net.intelryzen.ultimatequest.item;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.item.custom.BedrockBreakerItem;
import net.intelryzen.ultimatequest.item.custom.PigDestoyerItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class UQItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateQuestMod.MODID);

    public static final DeferredItem<Item> ULTRA_DUST = ITEMS.register("ultra_dust", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PROCESSED_ULTRA_DUST = ITEMS.register("processed_ultra_dust", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 1600;
        }
    });

    public static final DeferredItem<Item> ULTRA_INGOT = ITEMS.register("ultra_ingot", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PIG_DESTROYER = ITEMS.register("pig_destroyer", PigDestoyerItem::new);

    public static DeferredItem<Item> BEDROCK_BREAKER = ITEMS.register("bedrock_breaker", BedrockBreakerItem::new);

    public static DeferredItem<Item> OBSIDIAN_PROCESSED_ULTRA_DUST = ITEMS.register("obsidian_processed_ultra_dust", () -> new Item(new Item.Properties()));

    public static DeferredItem<Item> DARK_MATTER = ITEMS.register("dark_matter", () -> new Item(new Item.Properties()));

    public static DeferredItem<ArmorItem> ULTRA_HELMET = ITEMS.register("ultra_helmet", () -> new ArmorItem(ModArmorMaterials.ULTRA_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static DeferredItem<ArmorItem> ULTRA_CHESTPLATE = ITEMS.register("ultra_chestplate", () -> new ArmorItem(ModArmorMaterials.ULTRA_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static DeferredItem<ArmorItem> ULTRA_LEGGINGS = ITEMS.register("ultra_leggings", () -> new ArmorItem(ModArmorMaterials.ULTRA_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static DeferredItem<ArmorItem> ULTRA_BOOTS = ITEMS.register("ultra_boots", () -> new ArmorItem(ModArmorMaterials.ULTRA_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
