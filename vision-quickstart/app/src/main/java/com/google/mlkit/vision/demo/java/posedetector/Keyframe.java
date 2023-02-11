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
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        // get tracking points from landmarks
        // calculate the angle of the tracked points
        PoseLandmark p1 = landmarks.get(toTrack.get(0));
        PoseLandmark p2 = landmarks.get(toTrack.get(1));
        PoseLandmark p3 = landmarks.get(toTrack.get(2));

        double actualAngle = Util.getAngle(p1.getPosition().x, p1.getPosition().y, p2.getPosition().x, p2.getPosition().y, p3.getPosition().x, p3.getPosition().y);

        System.out.println("Target angle: " + angle);
        System.out.println("Actual angle: " + actualAngle);

        return Math.abs(actualAngle - angle) < leniency;
    }
}

class DualPointDistance implements Point {
    private List<Double> target;
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
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        PoseLandmark point = landmarks.get(toTrack);
        double distance = Util.getDistance(point.getPosition().x, point.getPosition().y, target.get(0), target.get(1));

        System.out.println("Target position: " + target);
        System.out.println("Actual distance: " + point.getPosition().x + ", " + point.getPosition().y);

        return distance < leniency;
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
    public boolean isValidPoint(List<PoseLandmark> landmarks) {
        // Iterate through every point
        for (int i = 0; i < points.size(); i++)
            if (!points.get(i).isValidPoint(landmarks))
                return false;
        return true;
    }

    public boolean isWithinTime() {
        // TODO: Implement logic to determine if the timer has expired
        return true;
    }

    public void clearTimer() {
        startTime = null;
    }
}
