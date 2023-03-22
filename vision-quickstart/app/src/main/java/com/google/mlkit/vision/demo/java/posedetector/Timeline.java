package com.google.mlkit.vision.demo.java.posedetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.mlkit.vision.demo.java.sqlite.DatabaseHelper;
import com.google.mlkit.vision.demo.java.sqlite.MediaPipeAppContract.TimelineDataEntry;
import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// An object that encapsulates a series of exercises
public class Timeline {

    public List<Exercise> exercises = null;
    public int currentIndex = 0;
    public int timelineRepeat = 0;
    public int exerciseUnavailable;
    public LocalDateTime startTime;
    // Indicates whether we can validate landmarks against this timeline
    public boolean ready = false;
    private DatabaseHelper dbHelper;
    String name;

    public Timeline(Context context, JSONObject timelineJson, String _name) {
        name = _name;

        // Read each exercise stored in the json
        try {
            //gets all the exercise jsons from the timeline
            JSONArray allExerciseJson = (JSONArray) timelineJson.get("timeline");
            exerciseUnavailable = allExerciseJson.length();
            exercises = new ArrayList<>(Collections.nCopies(exerciseUnavailable, null));

            //loops through all the exercise jsons
            for (int i = 0; i < allExerciseJson.length(); i++) {
                //adds this exercise to the list
                String name = ((JSONObject) allExerciseJson.get(i)).get("exercise").toString();
                fetchExercise(name, i);
            }
            currentIndex = 0;
        } catch (JSONException e) {
            System.out.println(e);
        }
        startTime = LocalDateTime.now();
        dbHelper = new DatabaseHelper(context);
    }

    public void validatePose(List<PoseLandmark> landmarks) {

        if (exercises.size() == 0)
            return;

        //passes the validation down to the current exercise (and checks if the exercise finished
        if (exercises.get(currentIndex).validatePose(landmarks)) {

            //moves to the next exercise
            currentIndex++;

            //if timeline has finished
            if (currentIndex >= exercises.size()) {

                // resets all exercises
                for (int i = 0; i < currentIndex; i++)
                    exercises.get(i).resetExercise();

                //resets the timeline
                currentIndex = 0;

                //increments the repeat counter
                timelineRepeat++;

                LocalDateTime endTime = LocalDateTime.now();
                float duration = Util.getDecimalSeconds(Duration.between(startTime, endTime));
                logTimelineResult(startTime.toString(), endTime.toString(), Float.toString(duration));
            }
        }
    }

    public List<String> getLandMarkInfo() {
        List<String> info = new ArrayList<>();

        //states how many times the timeline has been completed
        info.add("Timeline: " + name);

        if (exercises.size() == 0) {
            info.add("No exercises. Try to reload...");
            return info;
        }

        info.add("Repetitions: " + timelineRepeat);

        //states the current exercise name
        info.add("Current exercise: " + exercises.get(currentIndex).name);

        //gets the exercise's info
        info.addAll(exercises.get(currentIndex).getExerciseInfo());

        LocalDateTime endTime = LocalDateTime.now();
        float duration = Util.getDecimalSeconds(Duration.between(startTime, endTime));
        info.addAll(logTimelineResult(startTime.toString(), endTime.toString(), Float.toString(duration)));

        return info;
    }

    private void fetchExercise(String exerciseName, final int index) {
        if (exerciseName.contains(".json"))
            exerciseName = exerciseName.substring(0, exerciseName.length() - 5);
        String path = "Exercises/" + exerciseName;
        Util.fetchJson(path, new Util.jsonHandler() {
            @Override
            void parse(String name, JSONObject response) {
                exercises.set(index, new Exercise(response, name));
                exerciseUnavailable -= 1;
                if (exerciseUnavailable == 0)
                    ready = true;
            }
        });
    }

    private List<String> logTimelineResult(String startTime, String endTime, String duration) {
        List<String> info = new ArrayList<>();

        ContentValues values = new ContentValues();
        values.put(TimelineDataEntry.TIMELINE_NAME, name);
        values.put(TimelineDataEntry.START_TIME, startTime);
        values.put(TimelineDataEntry.END_TIME, endTime);
        values.put(TimelineDataEntry.DURATION, duration);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        int rows = db.update(TimelineDataEntry.TABLE_NAME, values, "timelineName = ?", new String[]{name});

        if (rows == 0)
            db.insert(TimelineDataEntry.TABLE_NAME, null, values);

        Cursor cursor = db.query(TimelineDataEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(TimelineDataEntry.DURATION);
            int nameIndex = cursor.getColumnIndex(TimelineDataEntry.TIMELINE_NAME);
            if (columnIndex >= 0 && nameIndex >= 0) {
                String dur = cursor.getString(columnIndex);
                String name = cursor.getString(nameIndex);
                info.add("Timeline: " + name + " for " + dur + "s");
            }
        }
        db.close();

        return info;
    }
}
