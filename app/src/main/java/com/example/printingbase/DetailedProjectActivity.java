package com.example.printingbase;

import static com.example.printingbase.DataBaseHelper.FILAMENT_AMOUNT;
import static com.example.printingbase.DataBaseHelper.FILAMENT_NAME;
import static com.example.printingbase.DataBaseHelper.FILAMENT_TABLE;
import static com.example.printingbase.DataBaseHelper.IS_FINISHED;
import static com.example.printingbase.DataBaseHelper.IS_PRINTING;
import static com.example.printingbase.DataBaseHelper.PROJECT_ID;
import static com.example.printingbase.DataBaseHelper.PROJECT_TABLE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailedProjectActivity extends AppCompatActivity {

    Button btn_startProject, btn_finishProject, btn_deleteProject;
    TextView tv_projectTitleDetail, tv_timeDetail, tv_amountDetail, tv_usedDetail, tv_printingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_projectTitleDetail = findViewById(R.id.tv_projectTitleDetail);
        tv_timeDetail = findViewById(R.id.tv_timeDetail);
        tv_amountDetail = findViewById(R.id.tv_amountDetail);
        tv_usedDetail = findViewById(R.id.tv_usedDetail);
        tv_printingDetail = findViewById(R.id.tv_printingDetail);
        btn_startProject = findViewById(R.id.btn_startProject);
        btn_finishProject = findViewById(R.id.btn_finishProject);
        btn_deleteProject = findViewById(R.id.btn_deleteProject);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        projectDetail();
    }
    public void sendToMain(View v){
        Intent i = new Intent(this, ProjectActivity.class);
        startActivity(i);
    }

    public void projectDetail() {
        int selectedProjectId = getIntent().getIntExtra("selectedProject", 0);
        String queryString = "SELECT * FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = " + selectedProjectId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        String projectName = null;
        String projectFilament = null;
        int projectTime = 0;
        int projectFilamentNeeded = 0;
        boolean projectStarted = false;



        while (cursor.moveToNext()) {
            projectName = cursor.getString(1);
            projectFilament = cursor.getString(2);
            projectTime = cursor.getInt(3);
            projectStarted = cursor.getInt(4) == 1;
            projectFilamentNeeded = cursor.getInt(6);
        }

        if (projectName != null) {
            tv_projectTitleDetail.setText(projectName);
            tv_usedDetail.setText(projectFilament);
            tv_timeDetail.setText(String.valueOf(projectTime));
            tv_amountDetail.setText(String.valueOf(projectFilamentNeeded));
            tv_printingDetail.setText(String.valueOf(projectStarted));

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void startProject(View v) {
        int selectedProjectId = getIntent().getIntExtra("selectedProject", 0);
        String queryString = "UPDATE " + PROJECT_TABLE + " SET " + IS_PRINTING + " = 1 WHERE " + PROJECT_ID + " = " + selectedProjectId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(queryString);
    }

    public void deleteProject(View v) {
        int selectedProjectId = getIntent().getIntExtra("selectedProject", 0);
        String queryString = "DELETE FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = " + selectedProjectId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(queryString);
        Intent i = new Intent(this, ProjectActivity.class);
        startActivity(i);
    }

    public void finishProject(View v) {
        int selectedProjectId = getIntent().getIntExtra("selectedProject", 0);
        String queryString = "UPDATE " + PROJECT_TABLE + " SET " + IS_FINISHED + " = 1 WHERE " + PROJECT_ID + " = " + selectedProjectId;
        int filamentUsed = Integer.parseInt(tv_amountDetail.getText().toString());
        String filamentName = tv_usedDetail.getText().toString();
        String queryString2 = "UPDATE " + FILAMENT_TABLE + " SET " + FILAMENT_AMOUNT + " = " + FILAMENT_AMOUNT + " - " + filamentUsed + " WHERE " + FILAMENT_NAME + " = '" + filamentName + "'";
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(queryString);
        db.execSQL(queryString2);
        Intent i = new Intent(this, ProjectActivity.class);
        startActivity(i);
    }



}