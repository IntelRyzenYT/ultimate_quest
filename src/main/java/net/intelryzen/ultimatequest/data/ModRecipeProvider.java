package net.intelryzen.ultimatequest.data;

import net.intelryzen.ultimatequest.block.UQBlocks;
import net.intelryzen.ultimatequest.item.UQItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.BEDROCK_BREAKER.get())
                .pattern("AAA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', UQItems.OBSIDIAN_PROCESSED_ULTRA_DUST.get())
                .define('B', Items.STICK)
                .unlockedBy("has_processed_ultra_dust", has(UQItems.OBSIDIAN_PROCESSED_ULTRA_DUST))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.OBSIDIAN_PROCESSED_ULTRA_DUST.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.OBSIDIAN)
                .define('B', UQItems.PROCESSED_ULTRA_DUST)
                .unlockedBy("has_processed_ultra_dust", has(UQItems.PROCESSED_ULTRA_DUST))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.ULTRA_HELMET)
                .pattern("AAA")
                .pattern("A A")
                .pattern("   ")
                .define('A', UQItems.ULTRA_INGOT)
                .unlockedBy("has_ultra_ingot", has(UQItems.ULTRA_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.ULTRA_CHESTPLATE)
                .pattern("A A")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', UQItems.ULTRA_INGOT)
                .unlockedBy("has_ultra_ingot", has(UQItems.ULTRA_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.ULTRA_LEGGINGS)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .define('A', UQItems.ULTRA_INGOT)
                .unlockedBy("has_ultra_ingot", has(UQItems.ULTRA_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UQItems.ULTRA_BOOTS)
                .pattern("   ")
                .pattern("A A")
                .pattern("A A")
                .define('A', UQItems.ULTRA_INGOT)
                .unlockedBy("has_ultra_ingot", has(UQItems.ULTRA_INGOT))
                .save(recipeOutput);
    }
}
