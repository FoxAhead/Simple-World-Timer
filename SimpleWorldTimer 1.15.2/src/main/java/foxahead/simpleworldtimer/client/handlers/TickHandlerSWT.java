package foxahead.simpleworldtimer.client.handlers;

import foxahead.simpleworldtimer.Timer;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
