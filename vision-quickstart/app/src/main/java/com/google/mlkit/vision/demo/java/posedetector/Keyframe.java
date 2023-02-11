package com.google.mlkit.vision.demo.java.posedetector;

import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

interface Point {
    boolean isValidPoint(List<PoseLandmark> landmarks);
}

class TriPointAngle implements Point {
    private List<Integer> toTrack;
    private int angle;
    private double leniency;

    public TriPointAngle(List<Integer> _toTrack, int _angle, double _leniency) {
        toTrack = _toTrack;
        angle = _angle;
        leniency = _leniency;
    }

    public TriPointAngle(JSONObject json) {
        try {
            // TODO: Cast JSONArray into List manually
            toTrack = new ArrayList<>();
            angle = (int) json.get("angle");
            leniency = (int) json.get("leniency");
            JSONArray points = (JSONArray) json.get("toTrack");

            for (int i = 0; i < points.length(); i++) {
                toTrack.add((int) points.get(i));
            }
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        // TODO: Write the logic to determine if the current pose matches what the points describe
        return false;
    }
}

class DualPointPosition implements Point {
    private List<Double> target;
    private int toTrack;
    private double leniency;

    public DualPointPosition(List<Double> _target, int _toTrack, double _leniency) {
        target = _target;
        toTrack = _toTrack;
        leniency = _leniency;
    }

    public DualPointPosition(JSONObject json) {
        try {
            // TODO: Cast JSONArray into List manually
            target = new ArrayList<>();
            toTrack = (int) json.get("toTrack");
            leniency = (double) json.get("leniency");
            JSONArray points = (JSONArray) json.get("target");

            for (int i = 0; i < points.length(); i++) {
                target.add((Double) points.get(i));
            }
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        // TODO: Write the logic to determine if the current pose matches what the points describe
        return false;
    }
}

public class Keyframe implements Point {
    private List<Point> points;
    private Date startTime;
    private double timeLimit;

    public Keyframe(List<Point> _points, double _timeLimit)  {
        points = _points;
        timeLimit = _timeLimit;
        startTime = null;
    }

    public Keyframe(JSONObject json) {
        // TODO: Parse json object that contains all points into keyframes
        this.points = new ArrayList<>();
        try {
            JSONArray points = (JSONArray) json.get("points");
            timeLimit = Double.parseDouble(json.get("timeLimit").toString());
            for (int i = 0; i < points.length(); i ++) {
                JSONObject point = (JSONObject) points.get(i);
                switch ((String) point.get("pointType")) {
                    case "triPointAngle":
                        this.points.add(new TriPointAngle(point));
                        break;
                    case "pointPosition":
                        this.points.add(new DualPointPosition(point));
                        break;
                }
            }
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        // TODO: Implement the logic to invoke every point's validator
        return false;
    }

    public boolean isWithinTime() {
        // TODO: Implement logic to determine if the timer has expired
        return false;
    }

    public void clearTimer() {
        startTime = null;
    }
}
