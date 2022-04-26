package ru.flystar.travelrk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/**
 * Created by sergey on 04.11.2017.
 */
@SuppressWarnings("Duplicates")
class TestMain {
  private static final double STARTLAT = 44.596763;
  private static final double STARTLNG = 34.374094;
  private static final double HEIGHT = 135;
  private static final double defVangle = 6.0;
  private static final double dCoef = 0.92;

  public static void main(String[] args) {
    String a = "https://static.travelrk.ru/3d/Sudak/Sudakdata";
    System.out.println(a.substring(0,a.lastIndexOf("/")));
//    String s = "if (s !== null, if (startscene === null OR startscene === \"undefined\", set(startscene, get(s));); );\nif (startscene === null OR startscene === \"undefined\",\n      set(startscene, pano264);\n);";
//    String r = "\n    set(startscene, assa);\n";
//    System.out.println(s.replaceFirst("[^,]\\s+set\\(startscene,\\s(.*)\\);",r));
  }

  private static void printInfo(double dstLat, double dstLng, String name) {
    double dist = getDistance(STARTLAT, STARTLNG, dstLat, dstLng);
    int realmeters = (int) dist;
    double vangl = 90 - toDegrees(Math.atan(dist * dCoef / HEIGHT));
    double gangl = getHotSpotAzimuth(STARTLAT, STARTLNG, dstLat, dstLng);
    String style = "hotspot_ani_white";
    if (vangl < defVangle) {
      style = "hotspot_arrow";
      vangl = defVangle;
    }
    System.out.printf(Locale.ROOT, "<hotspot name='spot_" + name + "' ath='%.6f' atv='%.6f' style='" + style + "' tag='point' />\n", gangl, vangl);
  }

  private static double getHotSpotAzimuth(double lat, double lng, double latHs, double lngHs) {
    double result = toDegrees(atan2(
        cos(toRadians(latHs)) * sin(toRadians(lngHs - lng)),
        cos(toRadians(lat)) * sin(toRadians(latHs)) - sin(toRadians(lat)) * cos(toRadians(latHs)) * cos(toRadians(lngHs - lng))

    ));
    return result;
  }

  private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
    double R = 6371000;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }
}
