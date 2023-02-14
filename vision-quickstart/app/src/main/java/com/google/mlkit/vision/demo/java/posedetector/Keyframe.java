package com.google.mlkit.vision.demo.java.posedetector;

import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

interface Point {
    boolean isValidPoint(List<List<Double>> landmarks);
    String getInfo();
}

class TriPointAngle implements Point {
    private List<Integer> toTrack;
    private int angle;
    private double actualAngle;
    private double leniency;

    public TriPointAngle(List<Integer> _toTrack, int _angle, double _leniency) {
        toTrack = _toTrack;
        angle = _angle;
        leniency = _leniency;
    }

    public TriPointAngle(JSONObject json) {
        try {
            toTrack = new ArrayList<>();
            angle = (int) json.get("angle");
            leniency = Double.parseDouble(json.get("leniency").toString());
            JSONArray points = (JSONArray) json.get("toTrack");

            for (int i = 0; i < points.length(); i++) {
                toTrack.add((int) points.get(i));
            }
//            System.out.println("TriPointAngle");
//            System.out.println(angle);
//            System.out.println(toTrack);
//            System.out.println(leniency);
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {
        // get tracking points from landmarks
        // calculate the angle of the tracked points
        List<Double> p1 = landmarks.get(toTrack.get(0));
        List<Double> p2 = landmarks.get(toTrack.get(1));
        List<Double> p3 = landmarks.get(toTrack.get(2));

        actualAngle = Util.getAngle(p1.get(0), p1.get(1), p2.get(0), p2.get(1), p3.get(0), p3.get(1));

//        System.out.println("Target angle: " + angle);
//        System.out.println("Actual angle: " + actualAngle);

        return Math.abs(actualAngle - angle) < leniency;
    }

    @Override
    public String getInfo() {
        String info = "Target: " + angle + "\nActual: " + actualAngle;
        return info;
    }
}

class DualPointDistance implements Point {
    private List<Double> target;
    private List<Double> actual;
    private int toTrack;
    private double leniency;

    public DualPointDistance(List<Double> _target, int _toTrack, double _leniency) {
        target = _target;
        toTrack = _toTrack;
        leniency = _leniency;
    }

    public DualPointDistance(JSONObject json) {
        try {
            target = new ArrayList<>();
            toTrack = (int) json.get("toTrack");
            leniency = Double.parseDouble(json.get("leniency").toString());
            JSONArray points = (JSONArray) json.get("target");

            for (int i = 0; i < points.length(); i++) {
                target.add((Double) points.get(i));
            }
//            System.out.println("DualPointDistance");
//            System.out.println(target);
//            System.out.println(toTrack);
//            System.out.println(leniency);
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {
        // TODO: Use List<List<Double>> instead
        List<Double> point = landmarks.get(toTrack);
        actual = Arrays.asList((double) point.get(0), (double) point.get(1));
        double distance = Util.getDistance(actual.get(0), actual.get(1), target.get(0), target.get(1));
//        System.out.println("Target position: " + target);
//        System.out.println("Actual distance: " + actual.get(0) + ", " + actual.get(1));

        return distance < leniency;
    }

    @Override
    public String getInfo() {
        String info = "Target: " + target + "\nActual: " + actual;
        return info;
    }
}

public class Keyframe implements Point {
    private List<Point> points;
    private Date startTime;
    private double timeLimit;

    public Keyframe(List<Point> _points, double _timeLimit) {
        points = _points;
        timeLimit = _timeLimit;
        startTime = null;
    }

    public Keyframe(JSONObject json) {
        this.points = new ArrayList<>();
        try {
            timeLimit = Double.parseDouble(json.get("timeLimit").toString());
            JSONArray points = (JSONArray) json.get("points");

            for (int i = 0; i < points.length(); i++) {
                JSONObject point = (JSONObject) points.get(i);
                switch ((String) point.get("pointType")) {
                    case "triPointAngle":
                        this.points.add(new TriPointAngle(point));
                        break;
                    case "pointPosition":
                        this.points.add(new DualPointDistance(point));
                        break;
                }
            }
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public boolean isValidPoint(List<List<Double>> landmarks) {
        // TODO: Use List<List<Double>> instead
        // Iterate through every point
        for (int i = 0; i < points.size(); i++)
            if (!points.get(i).isValidPoint(landmarks))
                return false;
        return true;
    }

    @Override
    public String getInfo() {
        // TODO
        String info = "";

        for (int i = 0; i < points.size(); i++) {
            info += points.get(i).getInfo();
            info += "\n";
        }

        return info;
    }

    public boolean isWithinTime() {
        // TODO: Implement logic to determine if the timer has expired
        return true;
    }

    public void clearTimer() {
        startTime = null;
    }
}
