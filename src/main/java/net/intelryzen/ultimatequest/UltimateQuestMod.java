package net.intelryzen.ultimatequest;

import net.intelryzen.ultimatequest.block.UQBlocks;
import net.intelryzen.ultimatequest.block.entity.UQBlockEntities;
import net.intelryzen.ultimatequest.item.UQCreativeModeTabs;
import net.intelryzen.ultimatequest.item.UQItems;
import net.intelryzen.ultimatequest.recipe.UQRecipes;
import net.intelryzen.ultimatequest.screen.UQMenuTypes;
import net.intelryzen.ultimatequest.screen.custom.ProcessorBlockMenu;
import net.intelryzen.ultimatequest.screen.custom.ProcessorBlockScreen;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UltimateQuestMod.MODID)
public class UltimateQuestMod {

    public static final String MODID = "ultimatequest";

    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public UltimateQuestMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);



        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        UQCreativeModeTabs.register(modEventBus);

        UQItems.register(modEventBus);
        UQBlocks.register(modEventBus);

        UQBlockEntities.register(modEventBus);
        UQMenuTypes.register(modEventBus);

        UQRecipes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(UQItems.ULTRA_DUST);
            event.accept(UQItems.PROCESSED_ULTRA_DUST);
            event.accept(UQItems.ULTRA_INGOT);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(UQBlocks.ULTRA_DUST_ORE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = UltimateQuestMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(UQMenuTypes.PROCESSOR_MENU.get(), ProcessorBlockScreen::new);
        }
    }
}
