package net.licketysplitter.maplecraft.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties MAPLE_SYRUP_BOTTLE = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(.25f)
            .build();

    public static final FoodProperties VENISON = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F).build();

    public static final FoodProperties COOKED_VENISON = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.8F).build();

    public static final FoodProperties GREEN_APPLE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.3F).build();

}
