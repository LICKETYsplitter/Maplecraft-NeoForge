package net.licketysplitter.maplecraft.block.entity;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MaplecraftMod.MOD_ID);

    public static final Supplier<BlockEntityType<EvaporatorBlockEntity>> EVAPORATOR_BE =
            BLOCK_ENTITIES.register("evaporator_be", () -> new BlockEntityType<>(
                    EvaporatorBlockEntity::new, ModBlocks.EVAPORATOR.get()));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
