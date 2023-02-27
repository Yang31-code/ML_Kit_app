package com.google.mlkit.vision.demo.java.posedetector.pointTypes;


import java.util.ArrayList;
import java.util.List;

//unknown point type. Will always return true on tracking
public class UnknownPoint implements Point {
    public UnknownPoint() {
    }

    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {
        return true;
    }

    @Override
    public List<String> getInfo() {
        List<String> poseFeedback = new ArrayList<>();
        poseFeedback.add("Unknown point. Should be skipped automatically");
        return poseFeedback;
    }
}
