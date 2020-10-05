package foxahead.simpleworldtimer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import foxahead.simpleworldtimer.command.CommandSWT;

@Mod(modid = SimpleWorldTimer.MODID, name = "Simple World Timer", version = SimpleWorldTimer.VERSION)
public class SimpleWorldTimer {
  public static final String     MODID   = "SimpleWorldTimer";
  public static final String     VERSION = "1.2.0";
  @Instance(MODID)
  public static SimpleWorldTimer instance;
  @SidedProxy(clientSide = "foxahead.simpleworldtimer.ClientProxy", serverSide = "foxahead.simpleworldtimer.CommonProxy")
  public static CommonProxy      proxy;
  //public static PlayerTracker    playerTracker;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    ConfigSWT.loadConfig();
    proxy.registerHandlers();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
  }

  @EventHandler
  public void serverStarting(FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandSWT());
  }
}
