package ru.flystar.travelrk;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.Math.toRadians;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 14.05.2018.
 */
public class TestVangle {
  public static void main(String[] args) {
    double dCoef = 0.92;
    double HEIGHT = 0;
    for (int i = 1; i <= 10; i++) {
      for (int h = 1; h <= 10; h++) {
        HEIGHT = HEIGHT + 20;
        double d = HEIGHT / Math.tan(toRadians(i)) / dCoef;
        System.out.print((int) d + ";");
      }
      System.out.println();
      HEIGHT = 0;
    }

    double logoWidth = 356;
    double logoHeight = 166;
    double bgFrameWidth = (60 / logoHeight) * logoWidth;
    System.out.println(bgFrameWidth);

    Long rentaDays = 80L;
    Long pregressDays = 40L;
    BigDecimal percent = new BigDecimal(100);
    if (rentaDays.compareTo(pregressDays) >= 0) percent = BigDecimal.valueOf(((double) pregressDays / rentaDays) * 100);
    System.out.println(":" + rentaDays + " " + pregressDays + " " + percent);
    BigDecimal s = BigDecimal.ZERO;
    System.out.println(s.setScale(4));
  }

}
