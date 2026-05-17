package net.intelryzen.ultimatequest.recipe;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.sql.Ref;

public class UQRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, UltimateQuestMod.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, UltimateQuestMod.MODID);


    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ProcessorRecipe>> PROCESSOR_SERIALIZER =
            SERIALIZERS.register("processor", ProcessorRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ProcessorRecipe>> PROCESSOR_TYPE =
            TYPES.register("processor", () -> new RecipeType<ProcessorRecipe>() {
                @Override
                public String toString() {
                    return "processor";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RefinerRecipe>> REFINER_SERIALIZER =
            SERIALIZERS.register("refiner", RefinerRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<RefinerRecipe>> REFINER_TYPE =
            TYPES.register("refiner", () -> new RecipeType<RefinerRecipe>() {
                @Override
                public String toString() {
                    return "refiner";
                }
            });

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);

        TYPES.register(bus);
    }

}
