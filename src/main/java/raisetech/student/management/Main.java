package raisetech.student.management;

import org.apache.commons.lang3.StringUtils;

public class Main {
  public static void main(String[] args) {
    String testString = "  ";
    if (StringUtils.isBlank(testString)) {
      System.out.println("The string is blank.");
    } else {
      System.out.println("The string is not blank.");
    }
  }
}
