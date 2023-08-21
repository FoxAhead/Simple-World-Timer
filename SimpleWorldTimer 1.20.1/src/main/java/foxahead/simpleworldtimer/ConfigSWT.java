package foxahead.simpleworldtimer;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.LongValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ConfigSWT {

  public static ConfigSWT instance = new ConfigSWT();

  private ConfigSWT() {
  }

  //public static final Configuration  CONFIGURATION          = new Configuration(new File(Loader.instance().getConfigDir(),
  //                                                                                       "SimpleWorldTimer.cfg"));
  public static final ForgeConfigSpec CONFIG_SPEC;
  public static final ConfigSWTValues CONFIG_VALUES;
  public static final String          NEW_LINE;
  public static final int             PRESET_TOTAL_WORLD     = 0;
  public static final int             PRESET_MINECRAFT       = 1;
  public static final int             PRESET_STOPWATCH       = 2;
  public static final int             PRESET_SYSTEM          = 3;
  public static final int             PRESET_CUSTOM          = 4;
  public static final int             CLOCK_TYPE_TOTAL_WORLD = 0;
  public static final int             CLOCK_TYPE_MINECRAFT   = 1;
  public static final int             CLOCK_TYPE_STOPWATCH   = 2;
  public static final int             CLOCK_TYPE_SYSTEM      = 3;

  public static class ConfigSWTValues {

    private static BooleanValue        enable;
    private static BooleanValue        autoHide;
    private static IntValue            xPosition;
    private static IntValue            yPosition;
    private static IntValue            preset;
    private static IntValue            clockType;
    private static IntValue            startYear;
    private static ConfigValue<String> pattern1;
    private static ConfigValue<String> pattern2;
    private static LongValue           stopWatchStart;
    private static LongValue           stopWatchStop;

    ConfigSWTValues(ForgeConfigSpec.Builder builder) {
      builder.comment("General settings").push("general");
      enable         = builder.comment("Enable Simple World Timer").define("enable", true);
      autoHide       = builder.comment("Auto hide timer on various screens like inventory, chat, debug, no-GUI mode (F1), main menu etc.")
                              .define("autoHide", true);
      xPosition      = builder.comment("Relative horizontal position in %. From 0% to 100%.")
                              .defineInRange("xPosition", 0, 0, 100);
      yPosition      = builder.comment("Relative vertical position in %. From 0% to 100%.")
                              .defineInRange("yPosition", 10, 0, 100);
      preset         = builder.comment("Timer preset:" + NEW_LINE + "0 - Total World Time" + NEW_LINE
                                       + "1 - Minecraft Time" + NEW_LINE + "2 - Stopwatch" + NEW_LINE
                                       + "3 - System Time" + NEW_LINE + "4 - Custom configuration")
                              .defineInRange("preset", PRESET_TOTAL_WORLD, PRESET_TOTAL_WORLD, PRESET_CUSTOM);
      clockType      = builder.comment("Source of data for timer:" + NEW_LINE
                                       + "0 - Total time of world being ticked provided by getTotalWorldTime() function"
                                       + NEW_LINE
                                       + "1 - in-game time provided by getWorldTime() function. Used for day/night cycle."
                                       + NEW_LINE
                                       + "    Can be changed by 'time set' command. 0 ticks equals 6:00 AM. 24000 ticks for one Minecraft day."
                                       + NEW_LINE + "2 - Manual start/stop" + NEW_LINE + "3 - System clock;")
                              .defineInRange("clockType",
                                             CLOCK_TYPE_TOTAL_WORLD,
                                             CLOCK_TYPE_TOTAL_WORLD,
                                             CLOCK_TYPE_SYSTEM);
      startYear      = builder.comment("Starting year for Minecraft time. To make sense of its calendar representation.")
                              .defineInRange("startYear", 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
      pattern1       = builder.comment("Custom pattern for line 1" + NEW_LINE
                                       + "Pattern letters of standard java.text.SimpleDateFormat class used." + NEW_LINE
                                       + "Additional syntax take effect after:" + NEW_LINE + "&d - total days"
                                       + NEW_LINE + "&w - total ticks" + NEW_LINE
                                       + "&t - current two-sign ticks in current second (00-19)" + NEW_LINE
                                       + "If you additionally put it in square brackets then everything within will output only if included values are non-zero"
                                       + NEW_LINE
                                       + "Those should be quoted using single quotes (') to avoid initial interpretation by SimpleDateFormat")
                              .define("pattern1", "");
      pattern2       = builder.comment("Custom pattern for line 2").define("pattern2", "");
      stopWatchStart = builder.comment("When was Stopwatch started")
                              .defineInRange("stopWatchStart", 0, Long.MIN_VALUE, Long.MAX_VALUE);
      stopWatchStop  = builder.comment("When was Stopwatch stopped")
                              .defineInRange("stopWatchStop", 0, Long.MIN_VALUE, Long.MAX_VALUE);
      builder.pop();
    }
  }

  public static String patterns[][] = {{"'[&dd ]'HH:mm:ss", ""}, // Total world 
                                       {"dd.MM.yyyy", "'Day &d' HH:mm"}, // Minecraft
                                       {"'[&dd ]'HH:mm:ss.SSS", "'Ticks &w'"}, // Stopwatch
                                       {"dd.MM.yyyy", "HH:mm:ss"}}; // System

  public static List<Component> getPresetList() {
    return Arrays.asList(Component.translatable("options.swt.preset0"),
    		             Component.translatable("options.swt.preset1"),
    		             Component.translatable("options.swt.preset2"),
    		             Component.translatable("options.swt.preset3"),
    		             Component.translatable("options.swt.preset4"));
  }

  public static List<Component> getClockTypeList() {
    return Arrays.asList(Component.translatable("options.swt.clockType0"),
    		             Component.translatable("options.swt.clockType1"),
    		             Component.translatable("options.swt.clockType2"),
    		             Component.translatable("options.swt.clockType3"));
  }

  public static boolean getEnable() {
    return ConfigSWTValues.enable.get();//true
  }

  public static void setEnable(boolean enable) {
    ConfigSWTValues.enable.set(enable);
  }

  public static boolean getAutoHide() {
    return ConfigSWTValues.autoHide.get();//true
  }

  public static void setAutoHide(boolean autoHide) {
    ConfigSWTValues.autoHide.set(autoHide);
  }

  public static int getxPosition() {
    return ConfigSWTValues.xPosition.get();//0
  }

  public static void setxPosition(int xPosition) {
    ConfigSWTValues.xPosition.set(Mth.clamp(xPosition, 0, 100));
  }

  public static int getyPosition() {
    return ConfigSWTValues.yPosition.get();//0
  }

  public static void setyPosition(int yPosition) {
    ConfigSWTValues.yPosition.set(Mth.clamp(yPosition, 0, 100));
  }

  public static int getPreset() {
    return ConfigSWTValues.preset.get();//0
  }

  public static void setPreset(int preset) {
    ConfigSWTValues.preset.set(Mth.clamp(preset, 0, getPresetList().size() - 1));
  }

  public static int getClockType() {
    if (getPreset() == PRESET_CUSTOM) {
      return ConfigSWTValues.clockType.get();//0
    } else {
      return getPreset();
    }
  }

  public static void setClockType(int clockType) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWTValues.clockType.set(Mth.clamp(clockType, 0, getClockTypeList().size() - 1));
    }
  }

  public static int getStartYear() {
    return ConfigSWTValues.startYear.get();//0
  }

  public static void setStartYear(int startYear) {
    ConfigSWTValues.startYear.set(startYear);
  }

  public static String getPattern1() {
    if (getPreset() == PRESET_CUSTOM) {
      return ConfigSWTValues.pattern1.get();
    } else {
      return patterns[getPreset()][0];
    }
  }

  public static void setPattern1(String pattern1) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWTValues.pattern1.set(pattern1.trim());
    }
  }

  public static String getPattern2() {
    if (getPreset() == PRESET_CUSTOM) {
      return ConfigSWTValues.pattern2.get();
    } else {
      return patterns[getPreset()][1];
    }
  }

  public static void setPattern2(String pattern2) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWTValues.pattern2.set(pattern2.trim());
    }
  }

  public static long getStopWatchStart() {
    return ConfigSWTValues.stopWatchStart.get();
  }

  public static void setStopWatchStart(long stopWatchStart) {
    ConfigSWTValues.stopWatchStart.set(stopWatchStart);
  }

  public static long getStopWatchStop() {
    return ConfigSWTValues.stopWatchStop.get();
  }

  public static void setStopWatchStop(long stopWatchStop) {
    ConfigSWTValues.stopWatchStop.set(stopWatchStop);
  }

  static {
    //CONFIGURATION.load();
    //CONFIGURATION.save();
    NEW_LINE = System.getProperty("line.separator");
    final Pair<ConfigSWTValues, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigSWTValues::new);
    CONFIG_SPEC   = specPair.getRight();
    CONFIG_VALUES = specPair.getLeft();
  }

  @SubscribeEvent
  public void onLoad(final ModConfigEvent.Loading configEvent) {
    pushChanges();
  }

  @SubscribeEvent
  public void onFileChange(final ModConfigEvent.Reloading configEvent) {
    ((CommentedFileConfig) configEvent.getConfig().getConfigData()).load();
    pushChanges();
  }

  private void pushChanges() {
    ConfigSWTValues.enable.get();
    ConfigSWTValues.autoHide.get();
    ConfigSWTValues.xPosition.get();
    ConfigSWTValues.yPosition.get();
    ConfigSWTValues.preset.get();
    ConfigSWTValues.clockType.get();
    ConfigSWTValues.startYear.get();
    ConfigSWTValues.pattern1.get();
    ConfigSWTValues.pattern2.get();
    ConfigSWTValues.stopWatchStart.get();
    ConfigSWTValues.stopWatchStop.get();
  }
  /*public static void loadConfig() {
    try {
      System.out.println();
      CONFIGURATION.load();
      enable    = CONFIGURATION.get("general", "enable", true, "Enable Simple World Timer");
      autoHide  = CONFIGURATION.get("general",
                                    "autoHide",
                                    true,
                                    "Auto hide timer on various screens like inventory, chat, debug, no-GUI mode (F1), main menu etc.");
      xPosition = CONFIGURATION.get("general", "xPosition", 0, "Relative horizontal position in %. From 0% to 100%.");
      setxPosition(getxPosition());
      yPosition = CONFIGURATION.get("general", "yPosition", 10, "Relative vertical position in %. From 0% to 100%.");
      setyPosition(getyPosition());
      preset         = CONFIGURATION.get("general",
                                         "preset",
                                         PRESET_TOTAL_WORLD,
                                         "Timer preset:" + NEW_LINE + "0 - Total World Time" + NEW_LINE
                                           + "1 - Minecraft Time" + NEW_LINE + "2 - Stopwatch" + NEW_LINE
                                           + "3 - System Time" + NEW_LINE + "4 - Custom configuration");
      clockType      = CONFIGURATION.get("general",
                                         "clockType",
                                         CLOCK_TYPE_TOTAL_WORLD,
                                         "Source of data for timer:" + NEW_LINE
                                           + "0 - Total time of world being ticked provided by getTotalWorldTime() function"
                                           + NEW_LINE
                                           + "1 - in-game time provided by getWorldTime() function. Used for day/night cycle."
                                           + NEW_LINE
                                           + "    Can be changed by 'time set' command. 0 ticks equals 6:00 AM. 24000 ticks for one Minecraft day."
                                           + NEW_LINE + "2 - Manual start/stop" + NEW_LINE + "3 - System clock;"
                                           + NEW_LINE);
      startYear      = CONFIGURATION.get("general",
                                         "startYear",
                                         1,
                                         "Starting year for Minecraft time. To make sense of its calendar representation.");
      pattern1       = CONFIGURATION.get("general",
                                         "pattern1",
                                         "",
                                         "Custom pattern for line 1" + NEW_LINE
                                           + "Pattern letters of standard java.text.SimpleDateFormat class used."
                                           + NEW_LINE + "Additional syntax take effect after:" + NEW_LINE
                                           + "&d - total days" + NEW_LINE + "&w - total ticks" + NEW_LINE
                                           + "&t - current two-sign ticks in current second (00-19)" + NEW_LINE
                                           + "If you additionally put it in square brackets then everything within will output only if included values are non-zero"
                                           + NEW_LINE
                                           + "Those should be quoted using single quotes (') to avoid initial interpretation by SimpleDateFormat"
                                           + NEW_LINE);
      pattern2       = CONFIGURATION.get("general", "pattern2", "", "Custom pattern for line 2");
      stopWatchStart = CONFIGURATION.get("general", "stopWatchStart", "0", "When was Stopwatch started");
      stopWatchStop  = CONFIGURATION.get("general", "stopWatchStop", "0", "When was Stopwatch stopped");
    } finally {
      syncConfig();
    }
  }*/
  /*public static void syncConfig() {
    if (CONFIGURATION.hasChanged()) {
      CONFIGURATION.save();
    }
  }*/
}
