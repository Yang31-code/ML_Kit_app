package com.google.mlkit.vision.demo.java.posedetector.pointTypes;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PointAbovePoint implements Point {

    private List<Integer> toTrack;

    private Double actualBelow;
    private Double actualAbove;

    private double leniency;


    public PointAbovePoint(JSONObject json) {

        try {

            toTrack = new ArrayList<>();

            //to track parsing
            JSONArray toTrackJson = (JSONArray) json.get("toTrack"); //should only have two points
            toTrack.add((int) toTrackJson.get(0)); //adds the point to be below
            toTrack.add((int) toTrackJson.get(1)); //adds the second to be above

            //leniency parsing
            leniency = Double.parseDouble(json.get("leniency").toString());


        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }


    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {

        List<Double> toBeBelowVec2 = landmarks.get(toTrack.get(0));
        List<Double> toBeAboveVec2 = landmarks.get(toTrack.get(1));

        double toBeBelowY = toBeBelowVec2.get(1);
        double toBeAboveY = toBeAboveVec2.get(1);

        actualBelow = toBeBelowY;
        actualAbove = toBeAboveY;

        return toBeAboveY + leniency < toBeBelowY;
    }

    @Override
    public List<String> getInfo() {

        List<String> poseFeedback = new ArrayList<>();

        poseFeedback.add("Point that should be lower's y: " + String.format("%.1f", actualBelow));
        poseFeedback.add("Point that should be above's y: " + String.format("%.1f", actualAbove));

        return poseFeedback;
    }
}