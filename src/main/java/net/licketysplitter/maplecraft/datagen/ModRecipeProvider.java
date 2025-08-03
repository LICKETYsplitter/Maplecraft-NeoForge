package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.datagen.custom.SawmillRecipeBuilder;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

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

        //SAWMILL
        createMillingRecipesForWoodType(ModBlocks.MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_LOG, ModBlocks.MAPLE_WOOD, ModBlocks.STRIPPED_MAPLE_WOOD,
                ModBlocks.MAPLE_PLANKS, ModBlocks.MAPLE_SLAB, ModBlocks.MAPLE_STAIRS, ModBlocks.MAPLE_FENCE, ModBlocks.MAPLE_FENCE_GATE, Items.OAK_BOAT, ModBlocks.MAPLE_PRESSURE_PLATE,
                ModBlocks.MAPLE_BUTTON, ModBlocks.MAPLE_DOOR, ModBlocks.MAPLE_TRAPDOOR, Blocks.OAK_SIGN);

        createMillingRecipesForWoodType(ModBlocks.APPLE_LOG, ModBlocks.STRIPPED_APPLE_LOG, ModBlocks.APPLE_WOOD, ModBlocks.STRIPPED_APPLE_WOOD,
                ModBlocks.APPLE_PLANKS, ModBlocks.APPLE_SLAB, ModBlocks.APPLE_STAIRS, ModBlocks.APPLE_FENCE, ModBlocks.APPLE_FENCE_GATE, Items.OAK_BOAT, ModBlocks.APPLE_PRESSURE_PLATE,
                ModBlocks.APPLE_BUTTON, ModBlocks.APPLE_DOOR, ModBlocks.APPLE_TRAPDOOR, Blocks.OAK_SIGN);

        createMillingRecipesForWoodType(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD,
                Blocks.OAK_PLANKS, Blocks.OAK_SLAB, Blocks.OAK_STAIRS, Blocks.OAK_FENCE, Blocks.OAK_FENCE_GATE, Items.OAK_BOAT, Blocks.OAK_PRESSURE_PLATE,
                Blocks.OAK_BUTTON, Blocks.OAK_DOOR, Blocks.OAK_TRAPDOOR, Blocks.OAK_SIGN);

        createMillingRecipesForWoodType(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD,
                Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, Blocks.BIRCH_STAIRS, Blocks.BIRCH_FENCE, Blocks.BIRCH_FENCE_GATE, Items.BIRCH_BOAT, Blocks.BIRCH_PRESSURE_PLATE,
                Blocks.BIRCH_BUTTON, Blocks.BIRCH_DOOR, Blocks.BIRCH_TRAPDOOR, Blocks.BIRCH_SIGN);

        createMillingRecipesForWoodType(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD,
                Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_FENCE, Blocks.SPRUCE_FENCE_GATE, Items.SPRUCE_BOAT, Blocks.SPRUCE_PRESSURE_PLATE,
                Blocks.SPRUCE_BUTTON, Blocks.SPRUCE_DOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.SPRUCE_SIGN);

        createMillingRecipesForWoodType(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD,
                Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, Blocks.ACACIA_STAIRS, Blocks.ACACIA_FENCE, Blocks.ACACIA_FENCE_GATE, Items.ACACIA_BOAT, Blocks.ACACIA_PRESSURE_PLATE,
                Blocks.ACACIA_BUTTON, Blocks.ACACIA_DOOR, Blocks.ACACIA_TRAPDOOR, Blocks.ACACIA_SIGN);

        createMillingRecipesForWoodType(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD,
                Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_FENCE_GATE, Items.DARK_OAK_BOAT, Blocks.DARK_OAK_PRESSURE_PLATE,
                Blocks.DARK_OAK_BUTTON, Blocks.DARK_OAK_DOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.DARK_OAK_SIGN);

        createMillingRecipesForWoodType(Blocks.PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG, Blocks.PALE_OAK_WOOD, Blocks.STRIPPED_PALE_OAK_WOOD,
                Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_SLAB, Blocks.PALE_OAK_STAIRS, Blocks.PALE_OAK_FENCE, Blocks.PALE_OAK_FENCE_GATE, Items.PALE_OAK_BOAT, Blocks.PALE_OAK_PRESSURE_PLATE,
                Blocks.PALE_OAK_BUTTON, Blocks.PALE_OAK_DOOR, Blocks.PALE_OAK_TRAPDOOR, Blocks.PALE_OAK_SIGN);

        createMillingRecipesForWoodType(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD,
                Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB, Blocks.CHERRY_STAIRS, Blocks.CHERRY_FENCE, Blocks.CHERRY_FENCE_GATE, Items.CHERRY_BOAT, Blocks.CHERRY_PRESSURE_PLATE,
                Blocks.CHERRY_BUTTON, Blocks.CHERRY_DOOR, Blocks.CHERRY_TRAPDOOR, Blocks.CHERRY_SIGN);

        createMillingRecipesForWoodType(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD,
                Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_FENCE, Blocks.JUNGLE_FENCE_GATE, Items.JUNGLE_BOAT, Blocks.JUNGLE_PRESSURE_PLATE,
                Blocks.JUNGLE_BUTTON, Blocks.JUNGLE_DOOR, Blocks.JUNGLE_TRAPDOOR, Blocks.JUNGLE_SIGN);

        createMillingRecipesForWoodType(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD,
                Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, Blocks.MANGROVE_STAIRS, Blocks.MANGROVE_FENCE, Blocks.MANGROVE_FENCE_GATE, Items.MANGROVE_BOAT, Blocks.MANGROVE_PRESSURE_PLATE,
                Blocks.MANGROVE_BUTTON, Blocks.MANGROVE_DOOR, Blocks.MANGROVE_TRAPDOOR, Blocks.MANGROVE_SIGN);

        createMillingRecipesForWoodType(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE,
                Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_FENCE, Blocks.CRIMSON_FENCE_GATE, Items.OAK_BOAT, Blocks.CRIMSON_PRESSURE_PLATE,
                Blocks.CRIMSON_BUTTON, Blocks.CRIMSON_DOOR, Blocks.CRIMSON_TRAPDOOR, Blocks.CRIMSON_SIGN);

        createMillingRecipesForWoodType(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE,
                Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB, Blocks.WARPED_STAIRS, Blocks.WARPED_FENCE, Blocks.WARPED_FENCE_GATE, Items.OAK_BOAT, Blocks.WARPED_PRESSURE_PLATE,
                Blocks.WARPED_BUTTON, Blocks.WARPED_DOOR, Blocks.WARPED_TRAPDOOR, Blocks.WARPED_SIGN);

    }

    private void createMillingRecipesForWoodType(ItemLike log, ItemLike strippedLog, ItemLike wood, ItemLike strippedWood,
                                                 ItemLike planks, ItemLike slabs, ItemLike stairs, ItemLike fence, ItemLike fenceGate,
                                                 ItemLike boat, ItemLike pressurePlate, ItemLike button, ItemLike door,
                                                 ItemLike trapDoor, ItemLike sign){

        milling(log, strippedLog, 1);
        milling(log, wood, 1);
        milling(log, strippedWood, 1);
        createLogMilling(log, strippedLog, wood, strippedWood, planks, slabs, stairs, fence,
                fenceGate, boat, pressurePlate, button, door, trapDoor, sign);
        milling(strippedLog, strippedWood, 1);
        createLogMilling(strippedLog, strippedLog, wood, strippedWood, planks, slabs, stairs, fence,
                fenceGate, boat, pressurePlate, button, door, trapDoor, sign);
        milling(wood, strippedLog, 1);
        milling(wood, log, 1);
        milling(wood, strippedWood, 1);
        createLogMilling(wood, strippedLog, wood, strippedWood, planks, slabs, stairs, fence,
                fenceGate, boat, pressurePlate, button, door, trapDoor, sign);
        milling(strippedWood, strippedLog, 1);
        createLogMilling(strippedWood, strippedLog, wood, strippedWood, planks, slabs, stairs, fence,
                fenceGate, boat, pressurePlate, button, door, trapDoor, sign);

        milling(planks, Items.STICK, 2);
        milling(planks, slabs, 2);
        milling(planks, stairs, 1);
        milling(planks, fence, 1);
        milling(planks, fenceGate, 1);
        milling(planks, pressurePlate, 1);
        milling(planks, button, 1);
        milling(planks, sign, 1);
    }

    private void createLogMilling(ItemLike log, ItemLike strippedLog, ItemLike wood, ItemLike strippedWood,
                                                 ItemLike planks, ItemLike slabs, ItemLike stairs, ItemLike fence, ItemLike fenceGate,
                                                 ItemLike boat, ItemLike pressurePlate, ItemLike button, ItemLike door,
                                                 ItemLike trapDoor, ItemLike sign){

        milling(log, Blocks.CRAFTING_TABLE, 1);
        milling(log, planks, 4);
        milling(log, Items.STICK, 8);
        milling(log, slabs, 8);
        milling(log, stairs, 4);
        milling(log, fence, 4);
        milling(log, fenceGate, 4);
        milling(log, boat, 1);
        milling(log, pressurePlate, 4);
        milling(log, button, 4);
        milling(log, door, 4);
        milling(log, trapDoor, 4);
        milling(log, sign, 4);
    }

    private void milling(ItemLike input, ItemLike result, int count){
        new SawmillRecipeBuilder(
                new ItemStack(result, count),
                Ingredient.of(input))
                .unlockedBy("has_" + input, this.has(input))
                .save(output, getName(result) + "_from_" + getName(input) + "_from_milling");
    }

    private String getName(ItemLike itemLike){
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
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
