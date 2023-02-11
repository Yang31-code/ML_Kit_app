package com.google.mlkit.vision.demo.java.posedetector;


import java.util.List;
import java.util.ArrayList;

import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PoseTracker {

    private List<Keyframe> kfs;
    int currentKeyframe;
    boolean status;

    public PoseTracker(List<Keyframe> _kfs) {
        kfs = _kfs;
        currentKeyframe = 0;
        status = false;
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

    public boolean validatePose(List<PoseLandmark> landmarks) {
        Keyframe kf = kfs.get(currentKeyframe);

        boolean validPose = kf.isValidPoint(landmarks);
        boolean withinTime = kf.isWithinTime();
        if (!withinTime) {
            // reset the state
            for (int i = 0; i < currentKeyframe; i++) {
                kfs.get(i).clearTimer();
            }
            currentKeyframe = 0;
        } else if (!validPose) {
            // pose is not valid but is still within time
            // continue
        } else {
            // move to the next keyframe
            // or terminate if traversed all keyframes
            if (currentKeyframe == kfs.size() - 1) {
                status = true;
            } else {
                currentKeyframe++;
            }
        }
        return status;
    }

}
