package ru.flystar.travelrk;

import javax.persistence.Entity;
import java.util.*;

import static java.lang.Math.*;

/**
 * Created by sergey on 04.11.2017.
 */
@SuppressWarnings("Duplicates")
public class TestMain {
    private static final double STARTLAT = 46.110359;
    private static final double STARTLNG = 33.690350;
    private static final double HEIGHT = 135;
    private static final double defVangle = 6.0;
    private static final double dCoef = 0.92;

    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
        printInfo(46.115860, 33.692827, "Detploshadka");
        printInfo(46.111798, 33.689795, "Kinoteatr");
        printInfo(46.110802, 33.691142, "Perekrestok");
        printInfo(46.110292, 33.691817, "Samolet");
        printInfo(46.107901, 33.690317, "Bank");
        printInfo(46.110556, 33.686114, "PUD");
        printInfo(46.117172, 33.693416, "Diskoteka");
        printInfo(46.113801, 33.689904, "Obshaga15");
        printInfo(46.106766, 33.684606, "DYUSSHA");
        printInfo(46.106421, 33.683137, "DOM-A");
        printInfo(46.105723, 33.683095, "DOM-B");
        printInfo(46.105040, 33.683224, "DOM-V");
        printInfo(46.104385, 33.683341, "DOM-G");
        printInfo(46.107641, 33.684000, "Perekrestok-dyussha");
        printInfo(46.107452, 33.683213, "Vhod-v-detsad");
        printInfo(46.107840, 33.684209, "Ugol doma");
        printInfo(45.384414, 32.505866, "Vechniy ogon");
        printInfo(46.119159, 33.698252, "Motocross");
        printInfo(46.119472, 33.690650, "Most");
        printInfo(46.116181, 33.693790, "Lavochka1");
        printInfo(46.116420, 33.693212, "Lavochka2");
        printInfo(46.109753, 33.690427, "Detploshadka-center");
        printInfo(45.385273, 32.507701, "Visotka");

    }

    private static void printInfo(double dstLat, double dstLng, String name) {
        double dist = getDistance(STARTLAT, STARTLNG, dstLat, dstLng);
        int realmeters = (int) dist;
        double vangl = 90 - toDegrees(Math.atan(dist*dCoef / HEIGHT));
        double gangl = getHotSpotAzimuth(STARTLAT, STARTLNG, dstLat, dstLng);
        String style = "hotspot_ani_white";
        if (vangl < defVangle) {
            style = "hotspot_arrow";
            vangl = defVangle;
        }
        System.out.printf(Locale.ROOT, "<hotspot name='spot_" + name + "' ath='%.6f' atv='%.6f' style='"+style+"' tag='point' />\n", gangl, vangl);
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
