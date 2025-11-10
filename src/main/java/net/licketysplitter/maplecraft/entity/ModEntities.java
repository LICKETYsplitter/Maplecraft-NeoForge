package net.licketysplitter.maplecraft.entity;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.custom.ModBoatEntity;
import net.licketysplitter.maplecraft.entity.custom.ModChestBoatEntity;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MaplecraftMod.MOD_ID);



    /*
    public static final Supplier<EntityType<Boat>> APPLE_BOAT =
            ENTITY_TYPES.register("apple_boat", () -> EntityType.Builder.of(boatFactory(()-> ModItems.APPLE_BOAT.get()), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_boat"))));

     */

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (entityType, level) -> new ModBoatEntity(entityType, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> chestBoatItemGetter) {
        return (entityType, level) -> new ModChestBoatEntity(entityType, level, chestBoatItemGetter);
    }


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
