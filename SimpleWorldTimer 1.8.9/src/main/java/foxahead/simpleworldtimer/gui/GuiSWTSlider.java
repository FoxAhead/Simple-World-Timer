package foxahead.simpleworldtimer.gui;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class GuiSWTSlider extends GuiButton {

  public String              sliderText  = "";
  public int                 sliderValue = 100;
  private ISWTSliderObserver sliderObserver;
  public boolean             dragging;

  public GuiSWTSlider(int id, int x, int y, String text, int value, ISWTSliderObserver sliderObserver) {
    super(id, x, y, 200, 20, text);
    this.sliderText  = text;
    this.sliderValue = value;
    this.setSliderDisplayString();
    this.sliderObserver = sliderObserver;
  }

  public int getHoverState(boolean par1) {
    return 0;
  }

  protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
    if (this.enabled) {
      if (this.dragging) {
        setSliderByMouse(par2);
      }
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexturedModalRect(this.xPosition
        + (this.sliderValue * (this.width - 8) / 100), this.yPosition, 0, 66, 4, 20);
      this.drawTexturedModalRect(this.xPosition + (this.sliderValue * (this.width - 8) / 100)
        + 4, this.yPosition, 196, 66, 4, 20);
    }
  }

  public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
    if (super.mousePressed(par1Minecraft, par2, par3)) {
      setSliderByMouse(par2);
      this.dragging = true;
      return true;
    } else {
      return false;
    }
  }

  private void setSliderByMouse(int x) {
    int newSliderValue = MathHelper.clamp_int(Math.round((float) (x - (this.xPosition + 4)) * 100 / (this.width - 8)),
                                              0,
                                              100);
    if (this.sliderValue != newSliderValue) {
      this.sliderValue = newSliderValue;
      sliderObserver.sliderValueChanged(this);
      setSliderDisplayString();
    }
  }

  private void setSliderDisplayString() {
    this.displayString = this.sliderText + String.valueOf(this.sliderValue);
  }

  public void mouseReleased(int par1, int par2) {
    this.dragging = false;
  }
}
