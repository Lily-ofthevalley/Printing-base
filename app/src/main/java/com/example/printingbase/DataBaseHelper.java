package com.example.printingbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PROJECT_TABLE = "PROJECT_TABLE";
    public static final String PROJECT_NAME = "PROJECT_NAME";
    public static final String FILAMENT = "FILAMENT";
    public static final String PRINT_TIME = "PRINT_TIME";
    public static final String IS_PRINTING = "IS_PRINTING";
    public static final String IS_FINISHED = "IS_FINISHED";
    public static final String PROJECT_ID = "PROJECT_ID";
    public static final String FILAMENT_TABLE = "FILAMENT_TABLE";
    public static final String FILAMENT_ID = "FILAMENT_ID";
    public static final String FILAMENT_NAME = "FILAMENT_NAME";
    public static final String FILAMENT_BRAND = "FILAMENT_BRAND";
    public static final String FILAMENT_TYPE = "FILAMENT_TYPE";
    public static final String COLOR = "COLOR";
    public static final String FILAMENT_AMOUNT = "FILAMENT_AMOUNT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "3dprint,db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProjectTableStatement = "CREATE TABLE " + PROJECT_TABLE + " (" + PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROJECT_NAME + " TEXT, " + FILAMENT + " TEXT, " + PRINT_TIME + " INTEGER, " + IS_PRINTING + " BOOL, " + IS_FINISHED + " BOOL)";
        String createFilamentTableStatement= "CREATE TABLE " + FILAMENT_TABLE + " (" + FILAMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FILAMENT_NAME + " TEXT, " + FILAMENT_BRAND + " TEXT, " + FILAMENT_TYPE + " TEXT, " + COLOR + " TEXT, " + FILAMENT_AMOUNT + " INTEGER)";

        db.execSQL(createProjectTableStatement);
        db.execSQL(createFilamentTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
