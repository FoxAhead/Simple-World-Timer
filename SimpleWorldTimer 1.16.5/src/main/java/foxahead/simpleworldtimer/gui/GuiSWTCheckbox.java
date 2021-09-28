package foxahead.simpleworldtimer.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiSWTCheckbox extends Button {

  private static ResourceLocation buttonTexture = new ResourceLocation("textures/gui/container/beacon.png");
  public boolean                  State         = false;

  public GuiSWTCheckbox(int x, int y, ITextComponent text, boolean state, Button.IPressable onPress) {
    this(x, y, 200, 20, text, state, onPress);
  }

  public GuiSWTCheckbox(int x, int y, int width, int height, ITextComponent text, boolean state, Button.IPressable onPress) {
    super(x, y, width, height, text, onPress);
    this.State = state;
  }

  @Override
  public void onPress() {
    this.State = !this.State;
    super.onPress();
  }

  @Override
  public void renderButton(MatrixStack matrixStack, int par2, int par3, float partialTicks) {
    if (this.visible) {
      Minecraft    minecraft    = Minecraft.getInstance();
      FontRenderer fontrenderer = minecraft.font;
      minecraft.getTextureManager().bind(buttonTexture);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
      this.isHovered = par2 >= this.x && par3 >= this.y && par2 < this.x + this.width && par3 < this.y + this.height;
      int textColor = (this.isHovered()) ? 0xFFFFFFA0 : 0xFFE0E0E0;
      int boxColor  = (this.isHovered()) ? 0xFFFFFFA0 : 0xFF000000;
      drawEmptyRect(matrixStack, this.x + 2, this.y + 2, this.x + 20 - 3, this.y + 20 - 3, boxColor);
      if (this.State) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(matrixStack, this.x + 3, this.y + 3, 91, 223, 16, 16);
      }
      drawString(matrixStack, fontrenderer, this.getMessage(), this.x + 20 + 4, this.y + (this.height - 8) / 2, textColor);
    }
  }

  public void drawEmptyRect(MatrixStack matrixStack, int parX1, int parY1, int parX2, int parY2, int parColor) {
    hLine(matrixStack, parX1, parX2, parY1, parColor);
    hLine(matrixStack, parX1, parX2, parY2, parColor);
    vLine(matrixStack, parX1, parY1, parY2, parColor);
    vLine(matrixStack, parX2, parY1, parY2, parColor);
  }
}
