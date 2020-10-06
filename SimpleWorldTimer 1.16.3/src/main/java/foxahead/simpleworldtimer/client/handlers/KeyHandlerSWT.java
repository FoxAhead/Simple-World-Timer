package foxahead.simpleworldtimer.client.handlers;

import org.lwjgl.glfw.GLFW;

import foxahead.simpleworldtimer.ConfigSWT;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyHandlerSWT {

  private static Minecraft mc = Minecraft.getInstance();
  public static KeyBinding keySWT;

  public KeyHandlerSWT() {
    keySWT = new KeyBinding("options.swt.title", GLFW.GLFW_KEY_H, "Simple World Timer");
    ClientRegistry.registerKeyBinding(keySWT);
  }

  public void keyDown(KeyBinding kb) {
    if (kb == keySWT) {
      if (mc.gameSettings.keyBindSneak.isKeyDown()) {
        ConfigSWT.setEnable(!ConfigSWT.getEnable());
      } else {
        if (mc.currentScreen == null) {
          if (mc.isGameFocused()) {
            mc.displayGuiScreen(new GuiSWTOptions());
          }
        } else if (mc.currentScreen instanceof GuiSWTOptions) {
          if (!((GuiSWTOptions) mc.currentScreen).isTyping()) {
            ((GuiSWTOptions) mc.currentScreen).closeMe();
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void onKeyInputEvent(KeyInputEvent event) {
    if (keySWT.isPressed()) {
      keyDown(keySWT);
      System.out.println("onKeyInputEvent");
    }

  }
}
