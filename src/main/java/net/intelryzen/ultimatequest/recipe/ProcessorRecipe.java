package net.intelryzen.ultimatequest.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Map;

public record ProcessorRecipe(Ingredient inputItem, ItemStack output) implements Recipe<ProcessorRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(ProcessorRecipeInput input, Level level) {

        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(ProcessorRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UQRecipes.PROCESSOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UQRecipes.PROCESSOR_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ProcessorRecipe> {
        public static final MapCodec<ProcessorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ProcessorRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(ProcessorRecipe::output)
        ).apply(inst, ProcessorRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ProcessorRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, ProcessorRecipe::inputItem,
                ItemStack.STREAM_CODEC, ProcessorRecipe::output,
                ProcessorRecipe::new
        );

        @Override
        public MapCodec<ProcessorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ProcessorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
