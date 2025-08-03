package net.licketysplitter.maplecraft.datagen.custom;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SimpleRecipeBuilder implements RecipeBuilder {
    protected final ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    protected String group = null;

    public SimpleRecipeBuilder(ItemStack result) {
        this.result = result;

    }

    @Override
    public SimpleRecipeBuilder unlockedBy(String name, Criterion<?> criterion){
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SimpleRecipeBuilder group(@Nullable String group){
        this.group = group;
        return this;
    }

    @Override
    public Item getResult(){
        return this.result.getItem();
    }

}
