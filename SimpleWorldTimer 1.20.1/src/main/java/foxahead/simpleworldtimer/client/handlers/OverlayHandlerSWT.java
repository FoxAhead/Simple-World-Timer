package foxahead.simpleworldtimer.client.handlers;

import foxahead.simpleworldtimer.Timer;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayHandlerSWT {
  public final Minecraft minecraft = Minecraft.getInstance();
  private static Timer timer = new Timer();

  @SubscribeEvent()
  public void renderGameOverlay(RenderGuiOverlayEvent event) {
    /*if (event.getType() != RenderGuiOverlayEvent.ElementType.ALL) {
      return;
    }*/
    if (!minecraft.options.hideGui && !minecraft.options.renderDebug) {
      GuiGraphics guiGraphics = event.getGuiGraphics();
      guiGraphics.pose().pushPose();
      Window window = event.getWindow();
      timer.drawTick(window, guiGraphics);
      guiGraphics.pose().popPose();
    }
  }

}
