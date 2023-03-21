package com.google.mlkit.vision.demo.java.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "exerciseLog.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String CREATE_ENTRY =
            "CREATE TABLE " + MediaPipeAppContract.TimelineDataEntry.TABLE_NAME + " (" +
            MediaPipeAppContract.TimelineDataEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            MediaPipeAppContract.TimelineDataEntry.TIMELINE_NAME + TEXT_TYPE + COMMA_SEP +
            MediaPipeAppContract.TimelineDataEntry.START_TIME + TEXT_TYPE + COMMA_SEP +
            MediaPipeAppContract.TimelineDataEntry.END_TIME + TEXT_TYPE + COMMA_SEP +
            MediaPipeAppContract.TimelineDataEntry.DURATION + TEXT_TYPE + ")";
    public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + MediaPipeAppContract.TimelineDataEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRY);
        onCreate(db);
    }
}
