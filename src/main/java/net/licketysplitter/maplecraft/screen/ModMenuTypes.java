package net.licketysplitter.maplecraft.screen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MaplecraftMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>,MenuType<EvaporatorMenu>> EVAPORATOR_MENU =
            registryMenuType("evaporator_menu", EvaporatorMenu::new);
    public static final DeferredHolder<MenuType<?>,MenuType<SawmillMenu>> SAWMILL_MENU =
            registryMenuType("sawmill_menu", SawmillMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>,MenuType<T>> registryMenuType(
            String name, IContainerFactory<T> factory){
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
