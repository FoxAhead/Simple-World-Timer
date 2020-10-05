package foxahead.simpleworldtimer.client;

import foxahead.simpleworldtimer.CommonProxy;
import foxahead.simpleworldtimer.client.handlers.KeyHandlerSWT;
import foxahead.simpleworldtimer.client.handlers.TickHandlerSWT;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

  @Override
  public void registerHandlers() {
    super.registerHandlers();
    //TickRegistry.registerTickHandler(new TickHandlerSWT(), Side.CLIENT);
    //KeyBindingRegistry.registerKeyBinding(new KeyHandlerSWT());
    FMLCommonHandler.instance().bus().register(new TickHandlerSWT());
    FMLCommonHandler.instance().bus().register(new KeyHandlerSWT());
  }
}
