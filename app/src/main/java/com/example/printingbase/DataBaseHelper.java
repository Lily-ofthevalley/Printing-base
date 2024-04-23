package com.example.printingbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    public static final String FILAMENT_NEEDED = "FILAMENT_NEEDED";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "3dprint,db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProjectTableStatement = "CREATE TABLE " + PROJECT_TABLE + " (" + PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROJECT_NAME + " TEXT, " + FILAMENT + " TEXT, " + PRINT_TIME + " INTEGER, " + IS_PRINTING + " BOOL, " + IS_FINISHED + " BOOL, " + FILAMENT_NEEDED + " INTEGER)";
        String createFilamentTableStatement = "CREATE TABLE " + FILAMENT_TABLE + " (" + FILAMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FILAMENT_NAME + " TEXT, " + FILAMENT_BRAND + " TEXT, " + FILAMENT_TYPE + " TEXT, " + COLOR + " TEXT, " + FILAMENT_AMOUNT + " INTEGER)";

        db.execSQL(createProjectTableStatement);
        db.execSQL(createFilamentTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //these are the functions to edit and show the database in the app
    public boolean addOneProject(ProjectModel projectModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PROJECT_NAME, projectModel.getProjectName());
        cv.put(FILAMENT, projectModel.getFilament());
        cv.put(PRINT_TIME, projectModel.getPrintTime());
        cv.put(FILAMENT_NEEDED, projectModel.getFilamentNeeded());

        long insert = db.insert(PROJECT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addOneFilament(FilamentModel filamentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FILAMENT_NAME, filamentModel.getFilamentName());
        cv.put(FILAMENT_BRAND, filamentModel.getFilamentBrand());
        cv.put(FILAMENT_TYPE, filamentModel.getFilamentType());
        cv.put(COLOR, filamentModel.getColor());
        cv.put(FILAMENT_AMOUNT, filamentModel.getFilamentAmount());

        long insert = db.insert(FILAMENT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //This is the arraylist
    public List<ProjectModel> getProjects() {
        List<ProjectModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + PROJECT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new project objects. Put them into the return list.
            do {
                int id = cursor.getInt(0);
                String Name = cursor.getString(1);
                String Filament = cursor.getString(2);
                int Time = cursor.getInt(3);
                int FilamentNeeded = cursor.getInt(6);

                // Create a new ProjectModel object and add it to the return list
                ProjectModel projectModel = new ProjectModel(id, Name, Filament, Time, FilamentNeeded);
                returnList.add(projectModel);
            } while (cursor.moveToNext()); // Use moveToNext() instead of moveToFirst() to iterate through all rows

        }

        //close both the cursor and db.
        cursor.close();
        db.close();
        return returnList;
    }

    public List<FilamentModel> getFilaments() {
        List<FilamentModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + FILAMENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new project objects. Put them into the return list.
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String brand = cursor.getString(2);
                String type = cursor.getString(3);
                String color = cursor.getString(4);
                int amount = cursor.getInt(5);

                // Create a new ProjectModel object and add it to the return list
                FilamentModel filamentModel = new FilamentModel(id, name, brand, type, color, amount);
                returnList.add(filamentModel);
            } while (cursor.moveToNext()); // Use moveToNext() instead of moveToFirst() to iterate through all rows

        }
        // No need for an else block here

        //close both the cursor and db.
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ProjectModel> getFinishedProjects() {
        List<ProjectModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + PROJECT_TABLE + " WHERE " + IS_FINISHED + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String projectName = cursor.getString(1);
            String projectFilament = cursor.getString(2);
            int projectTime = cursor.getInt(3);
            int projectFilamentNeeded = cursor.getInt(6);

            // Create a new ProjectModel object and add it to the return list
            ProjectModel projectModel = new ProjectModel(id, projectName, projectFilament, projectTime, projectFilamentNeeded);
            returnList.add(projectModel);
        }

        //close both the cursor and db.
        cursor.close();
        db.close();
        return returnList;
    }
}

