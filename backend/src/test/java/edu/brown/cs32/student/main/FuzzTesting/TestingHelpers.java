package edu.brown.cs32.student.main.FuzzTesting;

    import java.util.concurrent.ThreadLocalRandom;

public class TestingHelpers {

  /**
   * This does not guarantee an alpha-numeric string, or anything
   * even remotely readable. The "first" and "last" parameters can be used to
   * prevent generating characters like comma (for CSV testing) or newlines,
   * or control characters that will mess up some terminals.
   * @param length The length of the string to generate
   * @param first the first ASCII-code in the character range allowed
   * @param last the last ASCII-code in the character range allowed
   * @return a random string of length bytes
   */
  public static String getRandomStringBounded(int length) {
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    StringBuilder sb = new StringBuilder();
    for(int iCount=0;iCount<length;iCount++) {
      // upper-bound is exclusive
      int code = r.nextInt();
      sb.append((char) code);
    }
    return sb.toString();
  }

  public static double getRandomNumBounded(int length) {
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    double number = 0;
    for(int iCount=0;iCount<length;iCount++) {
      // upper-bound is exclusive
       //number = r.nextDouble(first, last);
      number = r.nextDouble();
    }
    return number;
  }


}
