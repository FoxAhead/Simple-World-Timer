package foxahead.simpleworldtimer;

public class Formatter {

  private static String text;
  private static int    length;
  private static int    i;
  private static char   c;

  /**
   * @param parText
   *          - text to format
   * @param w
   *          - &t Total ticks from zero tick
   * @param t
   *          - &t current two-sign ticks in current second (00-19)
   * @param d
   *          - &d Total days from zero tick
   * @param dd
   *          - &D total days of year
   * @param yy
   *          - &Y total years from start year
   * @return - Formatted string
   */
  public static String format(String parText, long w, long t, long d, long dd, long yy, String subSeasonName, String seasonName) {
    text   = parText;
    length = parText.length();
    i      = -1;
    StringBuilder out      = new StringBuilder(length * 2);
    StringBuilder part     = new StringBuilder();
    StringBuilder buffer   = out;
    boolean       nonZero  = false;
    boolean       hasValue = false;
    while (true) {
      try {
        getNextChar();
        if (c == '[') {
          part.setLength(0);
          buffer   = part;
          nonZero  = false;
          hasValue = false;
          continue;
        }
        if (c == ']') {
          if (!hasValue) {
            out.append("[");
          }
          if (!hasValue || nonZero) {
            out.append(part);
          }
          if (!hasValue) {
            out.append("]");
          }
          buffer = out;
          continue;
        }
        if (c == '&') {
          getNextChar();
          switch (c) {
            case 'w' :
              buffer.append(Long.toString(w));
              nonZero |= w != 0;
              hasValue = true;
              break;
            case 't' :
              buffer.append(String.format("%02d", t));
              nonZero |= t != 0;
              hasValue = true;
              break;
            case 'd' :
              buffer.append(Long.toString(d));
              nonZero |= d != 0;
              hasValue = true;
              break;
            case 'D' :
              buffer.append(Long.toString(dd));
              nonZero |= dd != 0;
              hasValue = true;
              break;
            case 'Y' :
              buffer.append(Long.toString(yy));
              nonZero |= yy != 0;
              hasValue = true;
              break;
            case 'B' :
              buffer.append(subSeasonName);
              hasValue = true;
              break;
            case 'S' :
              buffer.append(seasonName);
              hasValue = true;
              break;
          }
          continue;
        }
        buffer.append(c);
      } catch (Exception e) {
        break;
      }
    }
    return buffer.toString();
  }

  private static void getNextChar() throws Exception {
    i++;
    if (i < length) {
      c = text.charAt(i);
      return;
    }
    throw new Exception();
  }
}
