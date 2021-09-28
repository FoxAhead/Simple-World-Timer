package foxahead.simpleworldtimer;

import foxahead.simpleworldtimer.client.handlers.KeyHandlerSWT;
import foxahead.simpleworldtimer.client.handlers.OverlayHandlerSWT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("simpleworldtimer")
public class SimpleWorldTimer {

  public static final String MODID   = "simpleworldtimer";
  public static final String VERSION = "1.1.2-beta";

  public SimpleWorldTimer() {
    //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStarting);

    MinecraftForge.EVENT_BUS.register(this);
    
    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigSWT.CONFIG_SPEC);
  }

  //private void commonSetup(FMLCommonSetupEvent event) {
    //ConfigSWT.loadConfig();
  //}

  public void clientSetup(FMLClientSetupEvent event) {
    //MinecraftForge.EVENT_BUS.register(new TickHandlerSWT());
    MinecraftForge.EVENT_BUS.register(new KeyHandlerSWT());
    MinecraftForge.EVENT_BUS.register(new OverlayHandlerSWT());
    //MinecraftForge.EVENT_BUS.register(new ChatHandlerSWT());
    //FMLJavaModLoadingContext.get().getModEventBus().register(ConfigSWT.instance);
  }

  //public void serverStarting(FMLServerStartingEvent event) {
    //CommandSWT.register(event.getCommandDispatcher());
  //}
}
