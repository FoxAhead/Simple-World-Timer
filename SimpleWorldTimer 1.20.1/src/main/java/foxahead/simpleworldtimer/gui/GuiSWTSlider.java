package foxahead.simpleworldtimer.gui;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiSWTSlider extends AbstractWidget {

  public String              sliderText  = "";
  public int                 sliderValue = 100;
  private ISWTSliderObserver sliderObserver;
  public boolean             dragging;
  public int                 id;

  public GuiSWTSlider(int id, int x, int y, String text, int value, ISWTSliderObserver sliderObserver) {
    super(x, y, 200, 20, Component.literal(text));
    this.sliderText  = text;
    this.sliderValue = value;
    this.setSliderDisplayString();
    this.sliderObserver = sliderObserver;
    this.id             = id;
  }

  public int getHoverState(boolean par1) {
    return 0;
  }

  @Override
  protected void renderWidget(GuiGraphics guiGraphics, int par1, int par2, float par3) {
    Minecraft minecraft = Minecraft.getInstance();
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();
    guiGraphics.blit(WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + 0 * 20, this.width / 2, this.height);
    guiGraphics.blit(WIDGETS_LOCATION,
                     this.getX() + this.width / 2,
                     this.getY(),
                     200 - this.width / 2,
                     46 + 0 * 20,
                     this.width / 2,
                     this.height);
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    int i = (this.isHoveredOrFocused() ? 2 : 1) * 20;
    guiGraphics.blit(WIDGETS_LOCATION, getX() + (this.sliderValue * (this.width - 8) / 100), getY(), 0, 46 + i, 4, 20);
    guiGraphics.blit(WIDGETS_LOCATION,
                     getX() + (this.sliderValue * (this.width - 8) / 100) + 4,
                     getY(),
                     196,
                     46 + i,
                     4,
                     20);
    int i2 = getFGColor();
    renderScrollingString(guiGraphics, minecraft.font, 2, i2 | Mth.ceil(this.alpha * 255.0F) << 24);
  }

  @Override
  protected void onDrag(double p_onDrag_1_, double p_onDrag_3_, double p_onDrag_5_, double p_onDrag_7_) {
    // if (this.visible) {
    // if (this.dragging) {
    setSliderByMouse((int) p_onDrag_1_);
    // }
    // GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    // this.blit(this.x + (this.sliderValue * (this.width - 8) / 100), this.y, 0, 66, 4, 20);
    // this.blit(this.x + (this.sliderValue * (this.width - 8) / 100) + 4, this.y, 196, 66, 4, 20);
    // }
  }

  @Override
  public void onClick(double p_onClick_1_, double p_onClick_3_) {
    this.setSliderByMouse((int) p_onClick_1_);
  }
  /*
   * public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) { if
   * (super.mousePressed(par1Minecraft, par2, par3)) { setSliderByMouse(par2); this.dragging = true;
   * return true; } else { return false; } }
   */

  @Override
  public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
    boolean flag = p_keyPressed_1_ == GLFW.GLFW_KEY_LEFT;
    if (flag || p_keyPressed_1_ == GLFW.GLFW_KEY_RIGHT) {
      int i = flag ? -1 : 1;
      this.setSliderValue(this.sliderValue + i);
    }
    return false;
  }

  private void setSliderByMouse(int x) {
    setSliderValue(Math.round((float) (x - (getX() + 4)) * 100 / (this.width - 8)));
  }

  private void setSliderValue(int newSliderValue) {
    int clampedNewSliderValue = Mth.clamp(newSliderValue, 0, 100);
    if (this.sliderValue != clampedNewSliderValue) {
      this.sliderValue = clampedNewSliderValue;
      sliderObserver.sliderValueChanged(this);
      setSliderDisplayString();
    }
  }

  private void setSliderDisplayString() {
    this.setMessage(Component.literal(this.sliderText + String.valueOf(this.sliderValue)));
  }
  /*
   * public void mouseReleased(int par1, int par2) { this.dragging = false; }
   */

  @Override
  public void updateWidgetNarration(NarrationElementOutput p_259858_) {
    // TODO Auto-generated method stub
  }
}
