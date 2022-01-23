package foxahead.simpleworldtimer.client;

import foxahead.simpleworldtimer.CommonProxy;
import foxahead.simpleworldtimer.client.handlers.KeyHandlerSWT;
import foxahead.simpleworldtimer.client.handlers.TickHandlerSWT;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

  @Override
  public void registerHandlers() {
    super.registerHandlers();
    MinecraftForge.EVENT_BUS.register(new TickHandlerSWT());
    MinecraftForge.EVENT_BUS.register(new KeyHandlerSWT());
  }
}
