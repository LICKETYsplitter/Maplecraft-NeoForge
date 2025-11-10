package net.licketysplitter.maplecraft.item;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.item.custom.AntlerItem;
import net.licketysplitter.maplecraft.item.custom.MapleSyrupBottleItem;
import net.licketysplitter.maplecraft.item.custom.SapBucketItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MaplecraftMod.MOD_ID);

    public static final DeferredItem<Item> SUGAR_GLASS_SHARD = ITEMS.registerItem("sugar_glass_shard",
            (properties) -> new Item(properties.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sugar_glass_shard")))));

    public static final DeferredItem<Item> MAPLE_SYRUP_BOTTLE = ITEMS.registerItem("maple_syrup_bottle",
            (properties) -> new MapleSyrupBottleItem(properties.food(ModFoodProperties.MAPLE_SYRUP_BOTTLE, Consumables.HONEY_BOTTLE).stacksTo(16)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_syrup_bottle")))));

    public static final DeferredItem<Item> VENISON = ITEMS.registerItem("venison",
            (properties) -> new Item(properties.food(ModFoodProperties.VENISON, Consumables.DEFAULT_FOOD)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "venison")))));

    public static final DeferredItem<Item> COOKED_VENISON = ITEMS.registerItem("cooked_venison",
            (properties) -> new Item(properties.food(ModFoodProperties.COOKED_VENISON, Consumables.DEFAULT_FOOD)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "cooked_venison")))));

    /* ANTLERS */
    public static final DeferredItem<Item> ANTLER = ITEMS.registerItem("antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.COMMON).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "antler"))), "two"));
    /*public static final DeferredItem<Item> FOUR_POINT_ANTLER = ITEMS.registerItem("four_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.UNCOMMON).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "four_point_antler"))), "four"));
    public static final DeferredItem<Item> SIX_POINT_ANTLER = ITEMS.registerItem("six_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.RARE).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "six_point_antler"))), "six"));
    public static final DeferredItem<Item> EIGHT_POINT_ANTLER = ITEMS.registerItem("eight_point_antler",
            (properties) -> new AntlerItem(properties.rarity(Rarity.EPIC).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "eight_point_antler"))), "eight"));
     */
    public static final DeferredItem<Item> SAP_BUCKET = ITEMS.registerItem("sap_bucket",
            (properties) -> new SapBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sap_bucket")))));

    public static final DeferredItem<Item> GREEN_APPLE = ITEMS.registerItem("green_apple",
            (properties) -> new Item(new Item.Properties().food(ModFoodProperties.GREEN_APPLE)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "green_apple")))));

    /*
    public static final DeferredItem<Item> APPLE_BOAT = ITEMS.registerItem("apple_boat",
            (properties -> new BoatItem(EntityType.ACACIA_BOAT, new Item.Properties().stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_boat"))))));

     */

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
