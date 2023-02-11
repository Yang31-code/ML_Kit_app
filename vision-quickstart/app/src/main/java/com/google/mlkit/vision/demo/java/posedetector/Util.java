package com.google.mlkit.vision.demo.java.posedetector;

import java.lang.Math;

public class Util {
    double getDistance(double x1, double y1, double x2, double y2) {
        double xDistance = Math.pow(x1 - x2, 2);
        double yDistance = Math.pow(y1 - y2, 2);

        return Math.pow(xDistance + yDistance, 0.5);
    }
    double getAngle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double len1_2 = getDistance(x1, y1, x2, y2);
        double len2_3 = getDistance(x2, y2, x3, y3);
        double len1_3 = getDistance(x1, y1, x3, y3);

        double radian = Math.acos((Math.pow(len1_2, 2) + Math.pow(len2_3, 2) + Math.pow(len1_3, 2)) / (2 * len1_3 * len2_3));
        return Math.toDegrees(radian);
    }
}
