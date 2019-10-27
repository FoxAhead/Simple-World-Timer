package foxahead.simpleworldtimer;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

  @Override
  public void registerHandlers() {
    super.registerHandlers();
    TickRegistry.registerTickHandler(new TickHandlerSWT(), Side.CLIENT);
    KeyBindingRegistry.registerKeyBinding(new KeyHandlerSWT());
    /*
    SimpleWorldTimer.playerTracker = new PlayerTracker();
    GameRegistry.registerPlayerTracker(SimpleWorldTimer.playerTracker);
    MinecraftForge.EVENT_BUS.register(SimpleWorldTimer.playerTracker);
    */
  }
}
