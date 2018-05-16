package com.practicals.chris.a2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBController extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "HighScores";
    private static final int DATABASE_VERSION = 20; // Increment
    private static final String DATABASE_NAME = "CountdownDB";
    private static final String SCORE = "Score";
    private static final String DATETIME = "Datetime";
    private static final String LEVEL = "Difficulty";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATETIME + " DATETIME, "
            + SCORE + " INTEGER, "
            + LEVEL + " INTEGER);";
    private static final String SQL_INSERT_ENTRY =
            "INSERT INTO " + TABLE_NAME + " (" + DATETIME + ", " +
                    SCORE + ", " + LEVEL + ")" + " VALUES (datetime(), %s, %s);";
    private static final String SQL_DROP_TABLE =
            "DROP TABLE " + DBController.TABLE_NAME + ";";

    DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        db.execSQL(createDB());
    }

    String createDB() {
        Log.i("DB", SQL_CREATE_TABLE);
        return SQL_CREATE_TABLE;
    }

    String insertScore(int score, int level) {
        Log.i("DB", String.format(SQL_INSERT_ENTRY, score, level));
        return String.format(SQL_INSERT_ENTRY, score, level);
    }

    String dropTable() {
        return SQL_DROP_TABLE;
    }

}
