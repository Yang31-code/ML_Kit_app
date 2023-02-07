package com.google.mlkit.vision.demo.java.posedetector;

import java.util.List;

import com.google.mlkit.vision.pose.PoseLandmark;

public class PoseOracle {
    private List<Keyframe> kfs;
    int currentKeyframe;
    boolean status;

    public PoseOracle(List<Keyframe> _kfs) {
        kfs = _kfs;
        currentKeyframe = 0;
        status = false;
    }

    public void poseValidator(List<PoseLandmark> landmarks) {
        // TODO: Write the logic here to connect the lower-level functions
    }
}