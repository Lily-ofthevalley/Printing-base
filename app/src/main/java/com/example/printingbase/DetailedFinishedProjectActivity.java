package com.example.printingbase;

import static com.example.printingbase.DataBaseHelper.PROJECT_ID;
import static com.example.printingbase.DataBaseHelper.PROJECT_TABLE;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

import java.io.ByteArrayOutputStream;

public class DetailedFinishedProjectActivity extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    Button btn_deleteProject, btn_camera;
    TextView tv_projectTitleFinished, tv_timeFinished, tv_amountFinished, tv_usedFinished;
    ImageView iv_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_finished_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_deleteProject = findViewById(R.id.btn_deleteProject);
        tv_projectTitleFinished = findViewById(R.id.tv_projectTitleFinished);
        tv_timeFinished = findViewById(R.id.tv_timeFinished);
        tv_amountFinished = findViewById(R.id.tv_amountFinished);
        tv_usedFinished = findViewById(R.id.tv_usedFinished);
        btn_camera = findViewById(R.id.btn_camera);
        iv_camera = findViewById(R.id.iv_camera);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        projectDetail();
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream= new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            saveImageToDatabase(byteArray);

            iv_camera.setImageBitmap(imageBitmap);
        }
    }

    private void saveImageToDatabase(byte[] byteArray) {
        int selectedFinishedProjectId = getIntent().getIntExtra("selectedFinishedProject", 0);
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PROJECT_IMAGE, byteArray);
        String selection = PROJECT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(selectedFinishedProjectId)};
        db.update(PROJECT_TABLE, values, selection, selectionArgs);
    }

    public void sendToDFPA(View v){
        Intent i = new Intent(this, FinishedProjectActivity.class);
        startActivity(i);
    }

    public void projectDetail() {
        int selectedFinishedProjectId = getIntent().getIntExtra("selectedFinishedProject", 0);
        String queryString = "SELECT * FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = " + selectedFinishedProjectId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        String projectName = null;
        String projectFilament = null;
        int projectTime = 0;
        int projectFilamentNeeded = 0;
        byte[] projectImage = null;



        while (cursor.moveToNext()) {
            projectName = cursor.getString(1);
            projectFilament = cursor.getString(2);
            projectTime = cursor.getInt(3);
            projectFilamentNeeded = cursor.getInt(6);
            projectImage = cursor.getBlob(7);
        }

        if (projectName != null) {
            tv_projectTitleFinished.setText(projectName);
            tv_usedFinished.setText(projectFilament);
            tv_timeFinished.setText(String.valueOf(projectTime));
            tv_amountFinished.setText(String.valueOf(projectFilamentNeeded));
            if (projectImage != null && projectImage.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(projectImage, 0, projectImage.length);
                iv_camera.setImageBitmap(bitmap);
            }

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteProject(View v) {
        int selectedFinishedProjectId = getIntent().getIntExtra("selectedFinishedProject", 0);
        String queryString = "DELETE FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = " + selectedFinishedProjectId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(queryString);
        Intent i = new Intent(this, FinishedProjectActivity.class);
        startActivity(i);
    }
}