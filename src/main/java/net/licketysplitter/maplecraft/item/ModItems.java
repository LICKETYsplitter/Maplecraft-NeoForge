package net.licketysplitter.maplecraft.item;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.item.custom.AntlerItem;
import net.licketysplitter.maplecraft.item.custom.MapleSyrupBottleItem;
import net.licketysplitter.maplecraft.item.custom.SapBucketItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MaplecraftMod.MOD_ID);

    public static final DeferredItem<Item> SUGAR_GLASS_SHARD = ITEMS.registerItem("sugar_glass_shard",
            (properties) -> new Item(properties.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("sugar_glass_shard", MaplecraftMod.MOD_ID)))));

    public static final DeferredItem<Item> MAPLE_SYRUP_BOTTLE = ITEMS.registerItem("maple_syrup_bottle",
            (properties) -> new MapleSyrupBottleItem(properties.food(ModFoodProperties.MAPLE_SYRUP_BOTTLE, Consumables.HONEY_BOTTLE).stacksTo(16)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("maple_syrup_bottle", MaplecraftMod.MOD_ID)))));

    public static final DeferredItem<Item> VENISON = ITEMS.registerItem("venison",
            (properties) -> new Item(properties.food(ModFoodProperties.VENISON, Consumables.DEFAULT_FOOD)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("venison", MaplecraftMod.MOD_ID)))));

    public static final DeferredItem<Item> COOKED_VENISON = ITEMS.registerItem("cooked_venison",
            (properties) -> new Item(properties.food(ModFoodProperties.COOKED_VENISON, Consumables.DEFAULT_FOOD)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("cooked_venison", MaplecraftMod.MOD_ID)))));

    /* ANTLERS */
    public static final DeferredItem<Item> TWO_POINT_ANTLER = ITEMS.registerItem("two_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.COMMON).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("two_point_antler", MaplecraftMod.MOD_ID))), "two"));
    public static final DeferredItem<Item> FOUR_POINT_ANTLER = ITEMS.registerItem("four_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.UNCOMMON).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("four_point_antler", MaplecraftMod.MOD_ID))), "four"));
    public static final DeferredItem<Item> SIX_POINT_ANTLER = ITEMS.registerItem("six_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.RARE).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("six_point_antler", MaplecraftMod.MOD_ID))), "six"));
    public static final DeferredItem<Item> EIGHT_POINT_ANTLER = ITEMS.registerItem("eight_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.EPIC).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("eight_point_antler", MaplecraftMod.MOD_ID))), "eight"));

    public static final DeferredItem<Item> SAP_BUCKET = ITEMS.registerItem("sap_bucket",
            (properties) -> new SapBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("sap_bucket", MaplecraftMod.MOD_ID)))));

    public static final DeferredItem<Item> GREEN_APPLE = ITEMS.registerItem("green_apple",
            (properties) -> new Item(new Item.Properties().food(ModFoodProperties.GREEN_APPLE)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("green_apple", MaplecraftMod.MOD_ID)))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
