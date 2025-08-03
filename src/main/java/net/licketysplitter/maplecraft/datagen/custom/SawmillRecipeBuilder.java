package net.licketysplitter.maplecraft.datagen.custom;

import net.licketysplitter.maplecraft.recipe.custom.SawmillRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.data.DataMapProvider;

public class SawmillRecipeBuilder extends SimpleRecipeBuilder{
    private final Ingredient inputItem;


    public SawmillRecipeBuilder(ItemStack result, Ingredient inputItem) {
        super(result);
        this.inputItem = inputItem;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> resourceKey) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        SawmillRecipe recipe = new SawmillRecipe(this.inputItem, this.result);
        output.accept(resourceKey, recipe, advancement.build(
                resourceKey.location().withPrefix("/recipes")));
    }
}
