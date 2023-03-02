package foxahead.simpleworldtimer.client.handlers;

import org.lwjgl.glfw.GLFW;

import foxahead.simpleworldtimer.SimpleWorldTimer;
import foxahead.simpleworldtimer.ConfigSWT;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
// import net.minecraftforge.client.ClientRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SimpleWorldTimer.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyHandlerSWT {

  private static Minecraft mc = Minecraft.getInstance();
  public static KeyMapping keySWT;

  public static void keyDown(KeyMapping kb) {
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

  @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SimpleWorldTimer.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ModBusEvents {
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(keySWT = new KeyMapping("options.swt.title", GLFW.GLFW_KEY_H, "key.simpleworldtimer.categories.category_name"));
    }
  }
  
  @SubscribeEvent
  public static void onClientTick(ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      if (keySWT.consumeClick()) {
        keyDown(keySWT);
      }
    }
    
  }
  
/*  @SubscribeEvent
  public void onKeyInputEvent(Key event) {
    if (keySWT.consumeClick()) {
      keyDown(keySWT);
      System.out.println("onKeyInputEvent");
    }
  }
*/  
}
