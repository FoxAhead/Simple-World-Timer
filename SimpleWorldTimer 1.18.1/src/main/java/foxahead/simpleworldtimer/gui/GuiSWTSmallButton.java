package foxahead.simpleworldtimer.gui;

import java.util.List;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class GuiSWTSmallButton extends Button {

  private int                  index;
  private String               text = "";
  private int                  listSize;
  private List<Component>      variants;

  public GuiSWTSmallButton(int x, int y, Component text, List<Component> variants, int index,
    Button.OnPress onPress) {
    this(x, y, 200, 20, text, variants, index, onPress);
  }

  public GuiSWTSmallButton(int x, int y, int width, int height, Component text, List<Component> variants,
    int index, Button.OnPress onPress) {
    super(x, y, width, height, text, onPress);
    this.variants = variants;
    this.text     = text.getString();
    this.listSize = variants.size();
    this.setIndex(index);
  }

  @Override
  public void onPress() {
    index = (index + 1) % listSize;
    setDisplayString();
    super.onPress();
  }

  private void setDisplayString() {
    String displayString;
    displayString = variants.get(index).getString();
    if (!this.text.isEmpty()) {
      displayString = this.text + ": " + displayString;
    }
    this.setMessage(new TextComponent(displayString));
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
    setDisplayString();
  }

  public void setWidth(int width) {
    this.width = width;
  }
}
