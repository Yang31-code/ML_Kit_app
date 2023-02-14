package com.google.mlkit.vision.demo.java.posedetector;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Integer screenHeight = 0;
    public static Integer screenWidth = 0;

    //returns exact pixel values of the screen from a ratio vector array
    public static List<Double> ScreenRatioVectorToPixelVector(List<Double> ratio) {

        List<Double> toReturn = new ArrayList<>();

        toReturn.add(ratio.get(0) * Util.screenWidth);
        toReturn.add(ratio.get(1) * Util.screenHeight);

        return toReturn;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        double xDistance = Math.pow(x1 - x2, 2);
        double yDistance = Math.pow(y1 - y2, 2);

        return Math.pow(xDistance + yDistance, 0.5);
    }
    public static double getAngle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double len1_2 = getDistance(x1, y1, x2, y2);
        double len2_3 = getDistance(x2, y2, x3, y3);
        double len1_3 = getDistance(x1, y1, x3, y3);
        double radian = Math.acos((Math.pow(len1_2, 2) + Math.pow(len2_3, 2) - Math.pow(len1_3, 2)) / (2 * len1_2 * len2_3));
        double degrees = Math.toDegrees(radian);
        return degrees;
    }
}
