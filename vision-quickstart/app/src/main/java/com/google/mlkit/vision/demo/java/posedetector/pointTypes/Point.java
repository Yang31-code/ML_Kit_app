package com.google.mlkit.vision.demo.java.posedetector.pointTypes;

import java.util.List;

public interface Point {
        boolean isValidPoint(List<List<Double>> landmarks);

        List<String> getInfo();
}
