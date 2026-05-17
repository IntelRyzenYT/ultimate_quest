package net.intelryzen.ultimatequest.screen;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.screen.custom.ProcessorBlockMenu;
import net.intelryzen.ultimatequest.screen.custom.RefinerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class UQMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, UltimateQuestMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ProcessorBlockMenu>> PROCESSOR_MENU = registerMenuType("processor_menu", ProcessorBlockMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<RefinerMenu>> REFINER_MENU = registerMenuType("refiner_menu", RefinerMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
