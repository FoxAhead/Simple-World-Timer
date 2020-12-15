package foxahead.simpleworldtimer.client.handlers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
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
