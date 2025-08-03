package net.licketysplitter.maplecraft.recipe;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.recipe.custom.SawmillRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MaplecraftMod.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MaplecraftMod.MOD_ID);


    public static final DeferredHolder<RecipeType<?>, RecipeType<SawmillRecipe>> MILLING =
            RECIPE_TYPES.register("milling", () -> new RecipeType<SawmillRecipe>() {
                @Override
                public String toString() {
                    return "milling";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SawmillRecipe>> SAWMILL_SERIALIZER =
            SERIALIZERS.register("milling", SawmillRecipe.Serializer::new );

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
