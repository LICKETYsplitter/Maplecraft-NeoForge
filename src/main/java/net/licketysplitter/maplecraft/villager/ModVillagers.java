package net.licketysplitter.maplecraft.villager;

import com.google.common.collect.ImmutableSet;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, MaplecraftMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, MaplecraftMod.MOD_ID);

    public static final Holder<PoiType> LUMBERJACK_POI = POI_TYPES.register("lumberjack_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SAWMILL.get().getStateDefinition().getPossibleStates()),
                    1,1));

    public static final Holder<VillagerProfession> LUMBERJACK = VILLAGER_PROFESSION.register("lumberjack",
            () -> new VillagerProfession(Component.literal("lumberjack"), holder -> holder.value() == LUMBERJACK_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == LUMBERJACK_POI.value(), ImmutableSet.of(),
                    ImmutableSet.of(), SoundEvents.AXE_STRIP));

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
