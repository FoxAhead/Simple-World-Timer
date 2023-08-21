package foxahead.simpleworldtimer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GuiSWTCheckbox extends Button {

  private static ResourceLocation buttonTexture = new ResourceLocation("textures/gui/container/beacon.png");
  public boolean                  State         = false;

  public GuiSWTCheckbox(int x, int y, Component text, boolean state, Button.OnPress onPress) {
    this(x, y, 200, 20, text, state, onPress);
  }

  public GuiSWTCheckbox(int x, int y, int width, int height, Component text, boolean state, Button.OnPress onPress) {
    super(x, y, width, height, text, onPress, DEFAULT_NARRATION);
    this.State = state;
  }

  @Override
  public void onPress() {
    this.State = !this.State;
    super.onPress();
  }

  @Override
  public void renderWidget(GuiGraphics guiGraphics, int par2, int par3, float partialTicks) {
    if (this.visible) {
      Minecraft minecraft    = Minecraft.getInstance();
      Font      fontrenderer = minecraft.font;
      //minecraft.getTextureManager().bindForSetup(buttonTexture);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, buttonTexture);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
      this.isHovered = par2 >= getX() && par3 >= getY() && par2 < this.getX() + this.width
        && par3 < this.getY() + this.height;
      int textColor = (this.isHoveredOrFocused()) ? 0xFFFFFFA0 : 0xFFE0E0E0;
      int boxColor  = (this.isHoveredOrFocused()) ? 0xFFFFFFA0 : 0xFF000000;
      drawEmptyRect(guiGraphics,
                    this.getX() + 2,
                    this.getY() + 2,
                    this.getX() + 20 - 3,
                    this.getY() + 20 - 3,
                    boxColor);
      if (this.State) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(buttonTexture, this.getX() + 3, this.getY() + 3, 91, 223, 16, 16);
      }
      guiGraphics.drawString(fontrenderer,
                             this.getMessage(),
                             this.getX() + 20 + 4,
                             this.getY() + (this.height - 8) / 2,
                             textColor);
    }
  }

  public void drawEmptyRect(GuiGraphics guiGraphics, int parX1, int parY1, int parX2, int parY2, int parColor) {
    guiGraphics.hLine(parX1, parX2, parY1, parColor);
    guiGraphics.hLine(parX1, parX2, parY2, parColor);
    guiGraphics.vLine(parX1, parY1, parY2, parColor);
    guiGraphics.vLine(parX2, parY1, parY2, parColor);
  }
}
