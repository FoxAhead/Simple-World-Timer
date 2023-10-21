package foxahead.simpleworldtimer;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import com.mojang.blaze3d.vertex.PoseStack;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Timer2 {
/*
  private static final int      UPDATE_INTERVAL   = 10;
  private static final long     TICKS_AFTER_EPOCH = 1242715392000L;
  private static final int      WHITE_COLOR       = 0xFFFFFFFF;
  private static final int      GRAY_COLOR        = 0xFFE0E0E0;
  private static final TimeZone TZ_UTC            = TimeZone.getTimeZone("UTC");
  private long                  counter           = 1000000;
  private int                   startYear         = 1000000;
  private long                  startTicks        = 0;
  private Minecraft             mc                = Minecraft.getInstance();
  private String                language          = "";
  private int                   clockType         = -1;
  private SimpleDateFormat      sdf1              = new SimpleDateFormat();
  private SimpleDateFormat      sdf2              = new SimpleDateFormat();
  private DateTimeFormatter     dtf1;
  private DateTimeFormatter     dtf2;
  //private ScaledResolution      sRes;
  private String                pattern1          = "";
  private String                pattern2          = "";
  private boolean               needsPostFormat1  = true;
  private boolean               needsPostFormat2  = true;
  private String                outText1          = "";
  private String                outText2          = "";

  public void drawTick(MainWindow window, PoseStack matrix) {
    long ticks     = 0;
    int  sWidth    = 0;
    int  sHeight   = 0;
    int  x         = 0;
    int  y         = 0;
    int  textW     = 0;
    int  xPosition = 0;
    int  yPosition = 0;
    int  lines     = 0;
    int  color     = WHITE_COLOR;
    //
    if (mc.world == null)
      return;
    //if (mc.entityRenderer == null)
    //  return;
    if (mc.fontRenderer == null)
      return;
    if (!ConfigSWT.getEnable())
      return;
    if (ConfigSWT.getAutoHide()) {
      if (!(mc.currentScreen instanceof GuiSWTOptions)) {
        if (mc.gameSettings.showDebugInfo || mc.gameSettings.hideGUI || !mc.isGameFocused()) {
          return;
        }
      }
    }
    counter++;
    outText1 = "";
    outText2 = "";
    try {
      updateCache();
      //sWidth  = sRes.getScaledWidth();
      sWidth  = window.getScaledWidth();
      //sHeight = sRes.getScaledHeight();
      sHeight = window.getScaledHeight();
      switch (clockType) {
        case ConfigSWT.CLOCK_TYPE_TOTAL_WORLD :
          ticks = mc.world.getGameTime();//TotalWorldTime();
          formatOutTexts(ticks, convertTicksToDate(ticks - TICKS_AFTER_EPOCH));
          break;
        case ConfigSWT.CLOCK_TYPE_STOPWATCH :
          ticks = mc.world.getGameTime();//getTotalWorldTime();
          if (ConfigSWT.getStopWatchStart() > ConfigSWT.getStopWatchStop()) {
            ticks -= ConfigSWT.getStopWatchStart();
          } else {
            if (ticks / 10 % 2 == 0)
              color = GRAY_COLOR;
            ticks = ConfigSWT.getStopWatchStop() - ConfigSWT.getStopWatchStart();
            if (ticks == 0)
              color = WHITE_COLOR;
          }
          formatOutTexts(ticks, convertTicksToDate(ticks - TICKS_AFTER_EPOCH));
          break;
        case ConfigSWT.CLOCK_TYPE_MINECRAFT :
          ticks = mc.world.getDayTime();//getWorldTime();
          formatOutTexts(ticks, convertTicksToDate((ticks + 6000L) * 72L - TICKS_AFTER_EPOCH + startTicks));
          break;
        case ConfigSWT.CLOCK_TYPE_SYSTEM :
          formatOutTexts(0, ZonedDateTime.now());
          break;
      }
    } catch (Exception e) {
      //System.out.println(e.toString());
    }
    if (!outText1.isEmpty())
      lines++;
    if (!outText2.isEmpty())
      lines++;
    xPosition = ConfigSWT.getxPosition();
    yPosition = ConfigSWT.getyPosition();
    if (mc.gameSettings.hideGUI) {
      //mc.entityRenderer.setupOverlayRendering();
    }
    //RenderSystem.disableLighting();
    y = (sHeight - mc.fontRenderer.FONT_HEIGHT * lines - 1) * yPosition / 100 + 1;
    if (!outText1.isEmpty()) {
      textW = mc.fontRenderer.getStringWidth(outText1);
      x     = (sWidth - textW - 1) * xPosition / 100 + 1;
      mc.fontRenderer.func_243246_a(matrix, new StringTextComponent(outText1), x, y, color); //drawStringWithShadow
      y += mc.fontRenderer.FONT_HEIGHT;
    }
    if (!outText2.isEmpty()) {
      textW = mc.fontRenderer.getStringWidth(outText2);
      x     = (sWidth - textW - 1) * xPosition / 100 + 1;
      mc.fontRenderer.func_243246_a(matrix, new StringTextComponent(outText2), x, y, color); //drawStringWithShadow
    }
  }

  private void formatOutTexts(long ticks, TemporalAccessor temporal) {
    if (!pattern1.isEmpty()) {
      outText1 = dtf1.format(temporal);
      if (needsPostFormat1) {
        outText1 = postFormatOutText(ticks, temporal, outText1);
      }
    }
    if (!pattern2.isEmpty()) {
      outText2 = dtf2.format(temporal);
      if (needsPostFormat2) {
        outText2 = postFormatOutText(ticks, temporal, outText2);
      }
    }
  }

  private String postFormatOutText(long parTicks, TemporalAccessor temporal, String outText) {
    long days            = 0; // &d
    long totalDaysOfYear = 0; // &D
    long totalYears      = 0; // &Y
    switch (this.clockType) {
      case ConfigSWT.CLOCK_TYPE_TOTAL_WORLD :
        days = parTicks / 1728000L; // (One real day = 20 ticks * 60 seconds * 60 minutes * 24 hours)
        break;
      case ConfigSWT.CLOCK_TYPE_MINECRAFT :
        days = (parTicks + 30000L) / 24000L; // (One game day = 24000 ticks)
        Calendar cal = Calendar.getInstance(TZ_UTC);
        cal.setTime(date);
        cal.add(Calendar.HOUR, -6);
        totalYears = ((cal.get(Calendar.ERA) == GregorianCalendar.AD)
          ? cal.get(Calendar.YEAR)
          : -cal.get(Calendar.YEAR)) - ((this.startYear == 0) ? 1 : this.startYear);
        cal.add(Calendar.YEAR, (int) -totalYears);
        totalDaysOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        break;
    }
    return Formatter.format(outText, parTicks, parTicks % 20L, days, totalDaysOfYear, totalYears);
  }

  private Date convertTicksToDate(long parTicks) {
    return new Date(parTicks * 50);
  }

  private void updateCache() {
    //updateScaledResolution();
    updateStartYear();
    if (updateLanguage() | updateClockType() | updatePatterns()) {
      createNewSDF();
      createNewDTF();
    }
  }
  /*private void updateScaledResolution() {
    if (counter % UPDATE_INTERVAL == 0) {
      sRes = new ScaledResolution(mc);
    }
  }*/
/*
  private void createNewSDF() {
    try {
      sdf1 = new SimpleDateFormat("", new Locale(language.substring(0, 2), language.substring(3, 5)));
      sdf2 = new SimpleDateFormat("", new Locale(language.substring(0, 2), language.substring(3, 5)));
    } catch (Exception e) {
      sdf1 = new SimpleDateFormat("");
      sdf2 = new SimpleDateFormat("");
    }
    switch (clockType) {
      case ConfigSWT.CLOCK_TYPE_TOTAL_WORLD :
      case ConfigSWT.CLOCK_TYPE_STOPWATCH :
      case ConfigSWT.CLOCK_TYPE_MINECRAFT :
        sdf1.setTimeZone(TZ_UTC);
        sdf2.setTimeZone(TZ_UTC);
        break;
    }
    sdf1.applyPattern(pattern1);
    sdf2.applyPattern(pattern2);
  }

  private void createNewDTF() {
    try {
      dtf1 = DateTimeFormatter.ofPattern(pattern1, new Locale(language.substring(0, 2), language.substring(3, 5)));
      dtf2 = DateTimeFormatter.ofPattern(pattern2, new Locale(language.substring(0, 2), language.substring(3, 5)));
    } catch (Exception e) {
      dtf1 = DateTimeFormatter.ofPattern(pattern1);
      dtf2 = DateTimeFormatter.ofPattern(pattern2);
    }
  }

  private boolean updateLanguage() {
    if (counter % UPDATE_INTERVAL == 0) {
      String newValue = this.mc.getLanguageManager().getCurrentLanguage().getCode();//getLanguageCode();
      if (!this.language.equals(newValue)) {
        this.language = newValue;
        return true;
      }
    }
    return false;
  }

  private boolean updateClockType() {
    if (counter % UPDATE_INTERVAL == 0) {
      int newValue = ConfigSWT.getClockType();
      if (this.clockType != newValue) {
        this.clockType = newValue;
        return true;
      }
    }
    return false;
  }

  private boolean updateStartYear() {
    if (counter % UPDATE_INTERVAL == 0) {
      int newValue = ConfigSWT.getStartYear();
      if (this.startYear != newValue) {
        this.startYear = newValue;
        if (newValue <= 0) {
          newValue++;
        }
        try {
          Calendar cal = Calendar.getInstance(TZ_UTC);
          cal.set(newValue, 0, 1, 0, 0, 0);
          cal.set(Calendar.MILLISECOND, 0);
          this.startTicks = cal.getTimeInMillis() / 50L + TICKS_AFTER_EPOCH;
        } catch (Exception e) {
          this.startTicks = 0;
        }
        return true;
      }
    }
    return false;
  }

  private boolean updatePatterns() {
    boolean changed = false;
    if (counter % UPDATE_INTERVAL == 0) {
      String newValue1 = ConfigSWT.getPattern1();
      String newValue2 = ConfigSWT.getPattern2();
      if (!this.pattern1.equals(newValue1)) {
        this.pattern1    = newValue1;
        needsPostFormat1 = this.pattern1.contains("&");
        changed          = true;
      }
      if (!this.pattern2.equals(newValue2)) {
        this.pattern2    = newValue2;
        needsPostFormat2 = this.pattern2.contains("&");
        changed          = true;
      }
    }
    return changed;
  }*/
}
