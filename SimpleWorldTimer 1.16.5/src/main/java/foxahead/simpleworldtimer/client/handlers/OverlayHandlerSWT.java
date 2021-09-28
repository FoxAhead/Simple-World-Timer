package foxahead.simpleworldtimer.client.handlers;

import com.mojang.blaze3d.matrix.MatrixStack;
import foxahead.simpleworldtimer.Timer;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayHandlerSWT {
  public final Minecraft minecraft = Minecraft.getInstance();
  private static Timer timer = new Timer();

  @SubscribeEvent()
  public void renderGameOverlay(RenderGameOverlayEvent event) {
    if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
      return;
    }
    if (!minecraft.options.hideGui && !minecraft.options.renderDebug) {
      MatrixStack matrix = event.getMatrixStack();
      matrix.pushPose();
      MainWindow window = event.getWindow();
      timer.drawTick(window, matrix);
      matrix.popPose();
    }
  }

}
