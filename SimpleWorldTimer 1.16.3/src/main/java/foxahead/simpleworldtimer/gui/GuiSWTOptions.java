package foxahead.simpleworldtimer.gui;

import javax.annotation.Nonnull;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import foxahead.simpleworldtimer.ConfigSWT;
import foxahead.simpleworldtimer.client.handlers.KeyHandlerSWT;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiSWTOptions extends Screen implements ISWTSliderObserver {

  private static final int  COLOR1      = 0xFFFFFFFF;
  private static final int  COLOR2      = 0xFFE0E0E0;
  private GuiSWTCheckbox    checkBoxEnable;
  private GuiSWTCheckbox    checkBoxAutoHide;
  private GuiSWTSmallButton smallButtonPreset;
  private Button            buttonCustom;
  private GuiSWTSmallButton smallButtonClockType;
  private Button            buttonStart;
  private Button            buttonPause;
  private Button            buttonStop;
  private TextFieldWidget   startDateTextField;
  private TextFieldWidget   patternTextField1;
  private TextFieldWidget   patternTextField2;
  private Button            buttonSwap;
  private boolean           initialized = false;
  private int               xCoord      = 0;
  private int               yCoord      = 0;
  private int               yStep       = 24;
  //private int               focus       = -1;

  public GuiSWTOptions() {
    super(new TranslationTextComponent("options.title"));
  }

  @Override
  public void tick() {
    if (!initialized)
      return;
    this.startDateTextField.tick();
    this.patternTextField1.tick();
    this.patternTextField2.tick();
  }

  @Override
  public void init() {
    //Keyboard.enableRepeatEvents(true);
    this.minecraft.keyboardListener.enableRepeatEvents(true);
    xCoord           = this.width / 2 - 100;
    yCoord           = (this.height - yStep * 9) / 2;
    //this.buttonList.clear();
    checkBoxEnable   = this.addButton(new GuiSWTCheckbox(xCoord,
                                                         yCoord + yStep,
                                                         100,
                                                         20,
                                                         new TranslationTextComponent("options.swt.enable"),
                                                         ConfigSWT.getEnable(),
                                                         (onPress11) -> {
                                                                            this.actionPerformed(11);
                                                                          }));
    checkBoxAutoHide = this.addButton(new GuiSWTCheckbox(xCoord + 100,
                                                         yCoord + yStep,
                                                         100,
                                                         20,
                                                         new TranslationTextComponent("options.swt.autoHide"),
                                                         ConfigSWT.getAutoHide(),
                                                         (onPress12) -> {
                                                                            this.actionPerformed(12);
                                                                          }));
    this.addButton(new GuiSWTSlider(2, xCoord, yCoord + yStep * 2, "X: ", ConfigSWT.getxPosition(), this));
    this.addButton(new GuiSWTSlider(3, xCoord, yCoord + yStep * 3, "Y: ", ConfigSWT.getyPosition(), this));
    smallButtonPreset       = this.addButton(new GuiSWTSmallButton(xCoord,
                                                                   yCoord + yStep * 4,
                                                                   176,
                                                                   20,
                                                                   new TranslationTextComponent("options.swt.preset"),
                                                                   ConfigSWT.getPresetList(),
                                                                   ConfigSWT.getPreset(),
                                                                   (onPress21) -> {
                                                                                             this.actionPerformed(21);
                                                                                           }));
    buttonCustom            = this.addButton(new Button(xCoord
      + 180, yCoord + yStep * 4, 20, 20, new StringTextComponent("C"), (onPress4) -> {
                                this.actionPerformed(4);
                              }));
    smallButtonClockType    = this.addButton(new GuiSWTSmallButton(xCoord,
                                                                   yCoord + yStep * 5,
                                                                   75,
                                                                   20,
                                                                   StringTextComponent.EMPTY,                                            // I18n.getString("options.swt.clockType"),
                                                                   ConfigSWT.getClockTypeList(),
                                                                   ConfigSWT.getClockType(),
                                                                   (onPress22) -> {
                                                                     this.actionPerformed(22);
                                                                   }));
    this.startDateTextField = new TextFieldWidget(this.font,
                                                  xCoord + 150,
                                                  yCoord + yStep * 5,
                                                  50,
                                                  20,
                                                  StringTextComponent.EMPTY);                                                            //1
    startDateTextField.setResponder((onText1) -> {
      this.onTextField(1);
    });
    this.patternTextField1 = new TextFieldWidget(this.font,
                                                 xCoord,
                                                 yCoord + yStep * 6,
                                                 176,
                                                 20,
                                                 StringTextComponent.EMPTY); //2
    patternTextField1.setResponder((onText1) -> {
      this.onTextField(2);
    });
    this.patternTextField2 = new TextFieldWidget(this.font,
                                                 xCoord,
                                                 yCoord + yStep * 7,
                                                 176,
                                                 20,
                                                 StringTextComponent.EMPTY); //3
    patternTextField2.setResponder((onText1) -> {
      this.onTextField(3);
    });
    this.children.add(startDateTextField);
    this.children.add(patternTextField1);
    this.children.add(patternTextField2);
    buttonSwap = this.addButton(new Button(xCoord
      + 180, (int) (yCoord + yStep * 6.5F), 20, 20, new StringTextComponent(")"), (onPress5) -> {
        this.actionPerformed(5);
      }));
    this.addButton(new Button(xCoord, yCoord + yStep * 8, 200, 20, DialogTexts.GUI_DONE, (onPress0) -> {
      this.actionPerformed(0);
    }));
    buttonStart = this.addButton(new Button(xCoord
      + 102, yCoord + yStep * 5, 30, 20, new StringTextComponent(">"), (onPress6) -> {
                    this.actionPerformed(6);
                  }));
    buttonPause = this.addButton(new Button(xCoord
      + 136, yCoord + yStep * 5, 30, 20, new StringTextComponent("||"), (onPress7) -> {
                    this.actionPerformed(7);
                  }));
    buttonStop  = this.addButton(new Button(xCoord
      + 170, yCoord + yStep * 5, 30, 20, new StringTextComponent("[]"), (onPress8) -> {
                    this.actionPerformed(8);
                  }));
    updateButtons();
    initialized = true;
  }

  @Override
  public void onClose() {
    this.minecraft.keyboardListener.enableRepeatEvents(false);
  }

  public void actionPerformed(int parGuiButtonId) {
    String pattern1 = "";
    String pattern2 = "";
    if (parGuiButtonId == 0) {
      this.closeMe();
    }
    if (parGuiButtonId == 4) {
      pattern1 = ConfigSWT.getPattern1();
      pattern2 = ConfigSWT.getPattern2();
      int preset = ConfigSWT.getPreset();
      ConfigSWT.setPreset(ConfigSWT.PRESET_CUSTOM);
      ConfigSWT.setClockType(preset);
      ConfigSWT.setPattern1(pattern1);
      ConfigSWT.setPattern2(pattern2);
    }
    if (parGuiButtonId == 5) {
      pattern1 = ConfigSWT.getPattern1();
      ConfigSWT.setPattern1(ConfigSWT.getPattern2());
      ConfigSWT.setPattern2(pattern1);
    }
    if (parGuiButtonId >= 6 || parGuiButtonId <= 8) {
      long start = ConfigSWT.getStopWatchStart();
      long stop  = ConfigSWT.getStopWatchStop();
      long now   = this.minecraft.world.getGameTime();
      switch (parGuiButtonId) {
        case 6 :
          if (start <= stop) {
            ConfigSWT.setStopWatchStart(start + now - stop);
            ConfigSWT.setStopWatchStop(0);
          }
          break;
        case 7 :
          if (stop == 0) {
            ConfigSWT.setStopWatchStop(now);
          }
          break;
        case 8 :
          ConfigSWT.setStopWatchStart(now);
          ConfigSWT.setStopWatchStop(now);
          break;
      }
    }
    if (parGuiButtonId == 11) {
      ConfigSWT.setEnable(checkBoxEnable.State);
    }
    if (parGuiButtonId == 12) {
      ConfigSWT.setAutoHide(checkBoxAutoHide.State);
    }
    if (parGuiButtonId == 21) {
      ConfigSWT.setPreset(smallButtonPreset.getIndex());
    }
    if (parGuiButtonId == 22) {
      ConfigSWT.setClockType(smallButtonClockType.getIndex());
    }
    updateButtons();
  }

  private void onTextField(int id) {
    switch (id) {
      case 1 :
        try {
          ConfigSWT.setStartYear(Integer.parseInt(startDateTextField.getText()));
        } catch (Exception e) {
          ConfigSWT.setStartYear(0);
        }
        break;
      case 2 :
        ConfigSWT.setPattern1(patternTextField1.getText());
        //System.out.println(patternTextField1.getText());
        break;
      case 3 :
        ConfigSWT.setPattern2(patternTextField2.getText());
        break;
    }
  }

  @Override
  public boolean keyPressed(int par1, int par2, int par3) {
    if (super.keyPressed(par1, par2, par3)) {
      return true;
    } else {
      //this.startDateTextField.keyPressed(par1, par2, par3);
      //this.patternTextField1.keyPressed(par1, par2, par3);
      //this.patternTextField2.keyPressed(par1, par2, par3);
      /*    try {
      ConfigSWT.setStartYear(Integer.parseInt(startDateTextField.getText()));
          } catch (Exception e) {
      ConfigSWT.setStartYear(0);
          }
          ConfigSWT.setPattern1(patternTextField1.getText());
          ConfigSWT.setPattern2(patternTextField2.getText());*/
      /*if (par1 == GLFW.GLFW_KEY_TAB) {
        if (startDateTextField.isFocused()) {
          focus = 0;
        } else if (patternTextField1.isFocused()) {
          focus = 1;
        } else if (patternTextField2.isFocused()) {
          focus = 2;
        }
        focus = (focus + 1) % 3;
        startDateTextField.setFocused2(focus == 0);
        patternTextField1.setFocused2(focus == 1);
        patternTextField2.setFocused2(focus == 2);
      }*/
      if (par1 == GLFW.GLFW_KEY_ESCAPE) {
        this.closeMe();
      }
      if (par1 == KeyHandlerSWT.keySWT.getKey().getKeyCode() && !isTyping()) {
        this.closeMe();
      }
      return true;
    }
  }

  @Override
  public boolean mouseClicked(double par1, double par2, int par3) {
    this.startDateTextField.mouseClicked(par1, par2, par3);
    this.patternTextField1.mouseClicked(par1, par2, par3);
    this.patternTextField2.mouseClicked(par1, par2, par3);
    return super.mouseClicked(par1, par2, par3);
  }

  @Override
  public void sliderValueChanged(GuiSWTSlider slider) {
    if (slider.id == 2) {
      ConfigSWT.setxPosition(slider.sliderValue);
    }
    if (slider.id == 3) {
      ConfigSWT.setyPosition(slider.sliderValue);
    }
  }

  @Override
  public void render(@Nonnull MatrixStack stack, int par1, int par2, float par3) {
    if (!initialized)
      return;
    FontRenderer fontRenderer = minecraft.fontRenderer;
    this.renderBackground(stack);
    drawCenteredString(stack,
                       fontRenderer,
                       new TranslationTextComponent("options.swt.title"),
                       this.width / 2,
                       yCoord,
                       COLOR1);
    if (smallButtonClockType.getIndex() == ConfigSWT.CLOCK_TYPE_MINECRAFT) {
      drawCenteredString(stack,
                         fontRenderer,
                         new TranslationTextComponent("options.swt.startYear"),
                         xCoord + 120,
                         yCoord + yStep * 5 + 6,
                         COLOR2);
    }
    this.startDateTextField.render(stack, par1, par2, par3);
    this.patternTextField1.render(stack, par1, par2, par3);
    this.patternTextField2.render(stack, par1, par2, par3);
    super.render(stack, par1, par2, par3);
  }

  public void closeMe() {
    //ConfigSWT.syncConfig();
    this.minecraft.displayGuiScreen((Screen) null);
  }

  public boolean isTyping() {
    return (patternTextField1.isFocused() || patternTextField2.isFocused());
  }

  private void updateButtons() {
    smallButtonPreset.setIndex(ConfigSWT.getPreset());
    smallButtonClockType.setIndex(ConfigSWT.getClockType());
    switch (smallButtonPreset.getIndex()) {
      case ConfigSWT.PRESET_CUSTOM :
        smallButtonClockType.active = true;
        patternTextField1.setEnabled(true);
        patternTextField2.setEnabled(true);
        smallButtonClockType.setIndex(ConfigSWT.getClockType());
        buttonCustom.active = false;
        buttonSwap.active = true;
        break;
      default :
        smallButtonClockType.active = false;
        patternTextField1.setEnabled(false);
        patternTextField2.setEnabled(false);
        smallButtonClockType.setIndex(smallButtonPreset.getIndex());
        buttonCustom.active = true;
        buttonSwap.active = false;
        break;
    }
    switch (smallButtonClockType.getIndex()) {
      case ConfigSWT.CLOCK_TYPE_MINECRAFT :
        startDateTextField.setEnabled(true);
        startDateTextField.setVisible(true);
        break;
      default :
        startDateTextField.setEnabled(false);
        startDateTextField.setVisible(false);
        break;
    }
    buttonStart.visible = buttonPause.visible = buttonStop.visible = smallButtonClockType.getIndex() == ConfigSWT.CLOCK_TYPE_STOPWATCH;
    this.startDateTextField.setText(String.valueOf(ConfigSWT.getStartYear()));
    this.patternTextField1.setText(ConfigSWT.getPattern1());
    this.patternTextField2.setText(ConfigSWT.getPattern2());
  }

  public boolean doesGuiPauseGame() {
    return true;
  }
}
