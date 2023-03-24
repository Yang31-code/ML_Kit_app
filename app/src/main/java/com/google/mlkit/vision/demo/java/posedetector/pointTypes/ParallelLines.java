package com.google.mlkit.vision.demo.java.posedetector.pointTypes;

import com.google.mlkit.vision.demo.java.posedetector.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParallelLines implements Point {

    private List<Integer> toTrack;
    private double leniency;

    Double actualAng1 = 0.0;
    Double actualAng2 = 0.0;

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

        actualAng1 = (Util.getAngle(line1Start, line1End));
        actualAng2 = (Util.getAngle(line2Start, line2End));

        // we calculate the angle between the two lines, and the result must be in the range [0, 90]
        // this makes sure that a line with an angle 175 and a line with an angle 8 would be deemed parallel
        Double angDiff = Math.abs(actualAng1 - actualAng2);
        if (angDiff > 90)
            angDiff = 180 - angDiff;

        return angDiff < leniency;

    }

    @Override
    public List<String> getInfo() {

        List<String> poseFeedback = new ArrayList<>();

        poseFeedback.add("Line 1: " + actualAng1 + "degrees");
        poseFeedback.add("Line 2: " + actualAng2 + "degrees");

        return poseFeedback;
    }
}
