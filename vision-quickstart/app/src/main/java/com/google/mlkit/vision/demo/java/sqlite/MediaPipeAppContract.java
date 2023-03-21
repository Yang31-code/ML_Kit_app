package com.google.mlkit.vision.demo.java.sqlite;

import android.provider.BaseColumns;

public class MediaPipeAppContract {
    private MediaPipeAppContract() {}

    public static class TimelineDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "timelineData";
        public static final String TIMELINE_NAME = "timelineName";
        public static final String START_TIME = "startTime";
        public static final String END_TIME = "endTime";
        public static final String DURATION = "duration";
    }

}
