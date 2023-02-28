package com.google.mlkit.vision.demo.java.posedetector;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import com.google.mlkit.vision.demo.java.posedetector.pointTypes.Keyframe;
import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PoseTracker {

    private List<Keyframe> kfs;
    int currentKeyframe;
    int gestureCount;

    public PoseTracker(List<Keyframe> _kfs) {
        kfs = _kfs;
        currentKeyframe = 0;
        gestureCount = 0;
    }

    public PoseTracker(JSONObject json) {
        this.kfs = new ArrayList<>();
        try {
            JSONArray kfs = (JSONArray) json.get("keyframes");
            for (int i = 0; i < kfs.length(); i++) {
                JSONObject kf = (JSONObject) kfs.get(i);
                this.kfs.add(new Keyframe(kf));
            }
        } catch (JSONException e) {
            System.out.println(e);
            return;
        }
    }

    public void validatePose(List<PoseLandmark> landmarks) {
        Keyframe kf = kfs.get(currentKeyframe);

        List<List<Double>> landmarks_double = new ArrayList<>(landmarks.size());

        for (int i = 0; i < landmarks.size(); i++) {
            PoseLandmark lm = landmarks.get(i);
            landmarks_double.add(Arrays.asList((double) lm.getPosition().x, (double) lm.getPosition().y));
        }

        boolean validPose = kf.isValidPoint(landmarks_double);
        boolean withinTime = kf.isWithinTime();
        if (!withinTime) {
            // reset the state
            for (int i = 0; i < currentKeyframe + 1; i++) {
                kfs.get(i).clearTimer();
            }
            currentKeyframe = 0;
        } else if (!validPose) {
            // pose is not valid but is still within time
            // continue
            System.out.println("Invalid pose");
        } else {
            // move to the next keyframe
            // or terminate if traversed all keyframes
            System.out.println("Pass");
            if (currentKeyframe >= kfs.size() - 1) {
                resetPoseTracker();
            } else {
                kfs.get(currentKeyframe).clearTimer();
                currentKeyframe++;
            }
        }
    }

    public void resetPoseTracker() {

        gestureCount++;

        currentKeyframe = 0;

        for (int i = 0; i < kfs.size(); i++)
            kfs.get(i).clearTimer();
    }

    public int getPoseStatus() {
        return currentKeyframe + 1;
    }

    public List<String> getPoseInfo() {
        List<String> info = kfs.get(currentKeyframe).getInfo();
        info.add("Current Keyframe: " + currentKeyframe);
        return info;
    }
}
