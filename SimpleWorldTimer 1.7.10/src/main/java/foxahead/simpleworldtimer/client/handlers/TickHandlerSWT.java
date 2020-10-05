package foxahead.simpleworldtimer.client.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import foxahead.simpleworldtimer.Timer;

public class TickHandlerSWT {
  private static Timer timer = new Timer();

  public void tickStart() {}

  public void tickEnd() {
    timer.drawTick();
  }

  @SubscribeEvent
  public void onClientTickEvent(RenderTickEvent event) {
    if (event.phase == Phase.START) {
      tickStart();
    } else {
      tickEnd();
    }

  }
}
