package foxahead.simpleworldtimer;

public class Formatter {

  private static String text;
  private static int    length;
  private static int    i;
  private static char   c;

  public static String format(String parText, long d, long t, long w) {
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
          if(!hasValue) {
            out.append("[");
          }
          if (!hasValue || nonZero) {
            out.append(part);
          }
          if(!hasValue) {
            out.append("]");
          }
          buffer = out;
          continue;
        }
        if (c == '&') {
          getNextChar();
          switch (c) {
            case 'd' :
              buffer.append(Long.toString(d));
              nonZero |= d != 0;
              hasValue = true;
              break;
            case 't' :
              buffer.append(String.format("%02d", t));
              nonZero |= t != 0;
              hasValue = true;
              break;
            case 'w' :
              buffer.append(Long.toString(w));
              nonZero |= w != 0;
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
