package foxahead.simpleworldtimer.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiSWTCheckbox extends GuiButton {

  private static ResourceLocation buttonTexture = new ResourceLocation("textures/gui/container/beacon.png");
  public boolean                  State         = false;

  public GuiSWTCheckbox(int id, int x, int y, String text, boolean state) {
    this(id, x, y, 200, 20, text, state);
  }

  public GuiSWTCheckbox(int id, int x, int y, int width, int height, String text, boolean state) {
    super(id, x, y, width, height, text);
    this.State = state;
  }

  @Override
  public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
    if (super.mousePressed(par1Minecraft, par2, par3)) {
      this.State = !this.State;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void drawButton(Minecraft par1Minecraft, int par2, int par3, float partialTicks) {
    if (this.enabled) {
      par1Minecraft.getTextureManager().bindTexture(buttonTexture);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.hovered = par2 >= this.x && par3 >= this.y && par2 < this.x + this.width && par3 < this.y + this.height;
      int textColor = (this.hovered) ? 0xFFFFFFA0 : 0xFFE0E0E0;
      int boxColor  = (this.hovered) ? 0xFFFFFFA0 : 0xFF000000;
      drawEmptyRect(this.x + 2, this.y + 2, this.x + 20 - 3, this.y + 20 - 3, boxColor);
      if (this.State) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.x + 3, this.y + 3, 91, 223, 16, 16);
      }
      this.drawString(par1Minecraft.fontRenderer,
                      this.displayString,
                      this.x + 20 + 4,
                      this.y + (this.height - 8) / 2,
                      textColor);
    }
  }

  public void drawEmptyRect(int parX1, int parY1, int parX2, int parY2, int parColor) {
    drawHorizontalLine(parX1, parX2, parY1, parColor);
    drawHorizontalLine(parX1, parX2, parY2, parColor);
    drawVerticalLine(parX1, parY1, parY2, parColor);
    drawVerticalLine(parX2, parY1, parY2, parColor);
  }
}
