package foxahead.simpleworldtimer.client.handlers;

import com.mojang.blaze3d.vertex.PoseStack;
import foxahead.simpleworldtimer.Timer;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
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
      PoseStack poseStack = event.getPoseStack();
      poseStack.pushPose();
      Window window = event.getWindow();
      timer.drawTick(window, poseStack);
      poseStack.popPose();
    }
  }

}
