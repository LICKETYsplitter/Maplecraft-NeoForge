package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    public static class Runner extends RecipeProvider.Runner{
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider){
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Maplecraft Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        twoByTwoPacker(RecipeCategory.MISC, ModBlocks.SUGAR_GLASS.get(), ModItems.SUGAR_GLASS_SHARD.get());
        twoByTwoPacker(RecipeCategory.MISC, ModBlocks.MAPLE_SYRUP_BLOCK.get(), ModItems.MAPLE_SYRUP_BOTTLE.get());

        shapeless(RecipeCategory.MISC, ModItems.MAPLE_SYRUP_BOTTLE.get(), 4)
                .requires(ModBlocks.MAPLE_SYRUP_BLOCK.get())
                .requires(Items.GLASS_BOTTLE, 4)
                .unlockedBy(getHasName(ModBlocks.MAPLE_SYRUP_BLOCK.get()), has(ModBlocks.MAPLE_SYRUP_BLOCK.get()))
                .save(output);

        woodFromLogs( ModBlocks.MAPLE_WOOD.get(), ModBlocks.MAPLE_LOG.get());
        woodFromLogs(ModBlocks.STRIPPED_MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_LOG.get());
        planksFromLogs(ModBlocks.MAPLE_PLANKS.get(), ModTags.Items.MAPLE_LOGS, 4);
        unlockAndSave(stairBuilder(ModBlocks.MAPLE_STAIRS.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);
        slab(RecipeCategory.MISC, ModBlocks.MAPLE_SLAB.get(), ModBlocks.MAPLE_PLANKS.get());
        unlockAndSave(buttonBuilder(ModBlocks.MAPLE_BUTTON.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);
        pressurePlate(ModBlocks.MAPLE_PRESSURE_PLATE.get(), ModBlocks.MAPLE_PLANKS.get());
        unlockAndSave(fenceBuilder(ModBlocks.MAPLE_FENCE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);
        unlockAndSave(fenceGateBuilder(ModBlocks.MAPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);
        unlockAndSave(doorBuilder(ModBlocks.MAPLE_DOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);
        unlockAndSave(trapdoorBuilder(ModBlocks.MAPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), output);


        woodFromLogs(ModBlocks.APPLE_WOOD.get(), ModBlocks.APPLE_LOG.get());
        woodFromLogs(ModBlocks.STRIPPED_APPLE_WOOD.get(), ModBlocks.STRIPPED_APPLE_LOG.get());
        planksFromLogs(ModBlocks.APPLE_PLANKS.get(), ModTags.Items.APPLE_LOGS, 4);
        unlockAndSave(stairBuilder(ModBlocks.APPLE_STAIRS.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);
        slab(RecipeCategory.MISC, ModBlocks.APPLE_SLAB.get(), ModBlocks.APPLE_PLANKS.get());
        unlockAndSave(buttonBuilder(ModBlocks.APPLE_BUTTON.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);
        pressurePlate(ModBlocks.APPLE_PRESSURE_PLATE.get(), ModBlocks.APPLE_PLANKS.get());
        unlockAndSave(fenceBuilder(ModBlocks.APPLE_FENCE.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);
        unlockAndSave(fenceGateBuilder(ModBlocks.APPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);
        unlockAndSave(doorBuilder(ModBlocks.APPLE_DOOR.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);
        unlockAndSave(trapdoorBuilder(ModBlocks.APPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), output);

        //oreSmelting(output, List.of(ModBlocks.MAPLE_SYRUP_BLOCK.get()), RecipeCategory.MISC,
                //ModBlocks.SUGAR_GLASS.get(), 1.0f, 200, "sugar_glass");

        oreSmelting(output, List.of(ModItems.VENISON.get()), RecipeCategory.FOOD,
                ModItems.COOKED_VENISON.get(), 0.35F, 200, "cooked_venison");

        shapeless(RecipeCategory.MISC, Items.PURPLE_DYE, 2)
                .requires(ModBlocks.ASTER.get())
                .unlockedBy(getHasName(ModBlocks.ASTER.get()), has(ModBlocks.ASTER.get()))
                .save(output);
        shapeless(RecipeCategory.MISC, Items.WHITE_DYE, 2)
                .requires(ModBlocks.CATTAIL.get())
                .unlockedBy(getHasName(ModBlocks.CATTAIL.get()), has(ModBlocks.CATTAIL.get()))
                .save(output);
    }

    private void unlockAndSave(RecipeBuilder recipeBuilder, ItemLike unlockedBy, RecipeOutput recipeOutput){
        recipeBuilder.unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(recipeOutput);
    }

    protected void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                      ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                      ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, MaplecraftMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
