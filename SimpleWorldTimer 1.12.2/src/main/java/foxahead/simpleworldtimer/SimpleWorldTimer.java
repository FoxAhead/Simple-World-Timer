package foxahead.simpleworldtimer;

import foxahead.simpleworldtimer.command.CommandSWT;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = SimpleWorldTimer.MODID, name = "Simple World Timer", version = SimpleWorldTimer.VERSION, updateJSON = SimpleWorldTimer.UPDATE)
public class SimpleWorldTimer {
  public static final String     MODID   = "simpleworldtimer";
  public static final String     VERSION = "1.1.3.2";
  public static final String     UPDATE  = "https://raw.githubusercontent.com/FoxAhead/Simple-World-Timer/master/update/update.json";
  @Instance(MODID)
  public static SimpleWorldTimer instance;
  @SidedProxy(clientSide = "foxahead.simpleworldtimer.client.ClientProxy", serverSide = "foxahead.simpleworldtimer.CommonProxy")
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
