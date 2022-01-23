package foxahead.simpleworldtimer.client.handlers;

import org.lwjgl.glfw.GLFW;

import foxahead.simpleworldtimer.ConfigSWT;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.ClientRegistry;

public class KeyHandlerSWT {

  private static Minecraft mc = Minecraft.getInstance();
  public static KeyMapping keySWT;

  public KeyHandlerSWT() {
    keySWT = new KeyMapping("options.swt.title", GLFW.GLFW_KEY_H, "Simple World Timer");
    ClientRegistry.registerKeyBinding(keySWT);
  }

  public void keyDown(KeyMapping kb) {
    if (kb == keySWT) {
      if (mc.options.keyShift.isDown()) {
        ConfigSWT.setEnable(!ConfigSWT.getEnable());
      } else {
        if (mc.screen == null) {
          if (mc.isWindowActive()) {
            mc.setScreen(new GuiSWTOptions());
          }
        } else if (mc.screen instanceof GuiSWTOptions) {
          if (!((GuiSWTOptions) mc.screen).isTyping()) {
            ((GuiSWTOptions) mc.screen).closeMe();
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void onKeyInputEvent(KeyInputEvent event) {
    if (keySWT.consumeClick()) {
      keyDown(keySWT);
      System.out.println("onKeyInputEvent");
    }

  }
}
