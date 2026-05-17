package net.intelryzen.ultimatequest.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record RefinerRecipeInput(ItemStack input, FluidStack fluid) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? input : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public FluidStack fluid() {
        return fluid;
    }
}