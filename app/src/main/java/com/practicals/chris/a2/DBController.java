package com.practicals.chris.a2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 9; // Increment

    private static final String DATABASE_NAME = "CountdownDB";
    public static final String TABLE_NAME = "HighScores";
    public static final String SCORE = "Score";
    public static final String DATETIME = "Datetime";

    SQLiteDatabase myDB;


    private static String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATETIME + " DATETIME, "
            + SCORE + " INTEGER);";


    private static String SQL_INSERT_ENTRY =
            "INSERT INTO " + DBController.TABLE_NAME + "(" + DBController.DATETIME + ", " +
                    DBController.SCORE + ")" + " VALUES (datetime(), %s);";

    private static String SQL_GET_SCORES =
            "SELECT * FROM " + DBController.TABLE_NAME + " ORDER BY " + DBController.SCORE + "DESC LIMIT %s;";

    DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        createDB(db);
    }

    private void createDB(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);

        this.myDB = db;
    }

    String insertScore(int score) {
        return String.format(SQL_INSERT_ENTRY, score);
    }

    String getScores() {
        return SQL_GET_SCORES;
    }

}
