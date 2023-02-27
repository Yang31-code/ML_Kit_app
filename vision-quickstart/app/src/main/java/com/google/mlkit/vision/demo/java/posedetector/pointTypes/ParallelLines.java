package com.google.mlkit.vision.demo.java.posedetector.pointTypes;

import com.google.mlkit.vision.demo.java.posedetector.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.channels.ActorKt;

public class ParallelLines implements Point {

    private List<Integer> toTrack;
    private double leniency;

    Double actualLine1Slope = 0.0;
    Double actualLine2Slope = 0.0;

    public ParallelLines(JSONObject json) {
        try {

            //gets the indexes to track
            toTrack = new ArrayList<>();
            JSONArray landmarks = (JSONArray) json.get("toTrack");
            for (int i = 0; i < landmarks.length(); i++)
                toTrack.add((int) landmarks.get(i));

            //gets the angle (i think) leniency
            leniency = Double.parseDouble(json.get("leniency").toString());

        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }


    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {

        // get tracking points from landmarks
        List<Double> line1Start = landmarks.get(toTrack.get(0));
        List<Double> line1End = landmarks.get(toTrack.get(1));

        List<Double> line2Start = landmarks.get(toTrack.get(2));
        List<Double> line2End = landmarks.get(toTrack.get(3));


//        Double slope1 = Math.abs(GetSlope(line1Start, line1End));
//        Double slope2 = Math.abs(GetSlope(line2Start, line2End));
//
        Double slope1 = (GetSlope(line1Start, line1End));
        Double slope2 = (GetSlope(line2Start, line2End));

        actualLine1Slope = slope1;
        actualLine2Slope = slope2;

        return (slope1 > slope2 - leniency && slope1 < slope2 + leniency);

    }

    Double GetSlope(List<Double> start, List<Double> end) {

        if (start.get(0) == end.get(0))
            if (start.get(1) > end.get(1))
                return -90.0;
            else
                return 90.0;

        Double deltaX = start.get(0) - end.get(0);
        Double deltaY = start.get(1) - end.get(1);

        Double tanTheta = deltaY/deltaX;
        Double thetaRad = Math.atan(tanTheta);
        Double thetaDeg = Math.toDegrees(thetaRad);
        System.out.println("Degrees: " + thetaDeg + ", Radians: " + thetaRad);

        return thetaDeg;
    }

    @Override
    public List<String> getInfo() {

        List<String> poseFeedback = new ArrayList<>();

        poseFeedback.add("Line 1: " + actualLine1Slope + "degrees");
        poseFeedback.add("Line 2: " + actualLine2Slope + "degrees");

        return poseFeedback;
    }
}
