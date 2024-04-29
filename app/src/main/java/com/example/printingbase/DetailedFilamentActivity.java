package com.example.printingbase;

import static com.example.printingbase.DataBaseHelper.FILAMENT_ID;
import static com.example.printingbase.DataBaseHelper.FILAMENT_TABLE;
import static com.example.printingbase.DataBaseHelper.PROJECT_ID;
import static com.example.printingbase.DataBaseHelper.PROJECT_TABLE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailedFilamentActivity extends AppCompatActivity {

    Button btn_deleteFilament;
    TextView tv_detailFilamentName, tv_brand, tv_type, tv_color, tv_filLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_filament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_deleteFilament = findViewById(R.id.btn_deleteFilament);
        tv_detailFilamentName = findViewById(R.id.tv_detailFilamentName);
        tv_brand = findViewById(R.id.tv_brand);
        tv_type = findViewById(R.id.tv_type);
        tv_color = findViewById(R.id.tv_color);
        tv_filLeft = findViewById(R.id.tv_filLeft);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        filamentDetail();
    }

    public void filamentDetail() {
        int selectedFilament = getIntent().getIntExtra("selectedFilament", 0);
        String queryString = "SELECT * FROM " + FILAMENT_TABLE + " WHERE " + FILAMENT_ID + " = " + selectedFilament;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        String filamentName = null;
        String filamentBrand = null;
        String filamentType = null;
        String filamentColor = null;
        int filamentLeft = 0;



        while (cursor.moveToNext()) {
            filamentName = cursor.getString(1);
            filamentBrand = cursor.getString(2);
            filamentType = cursor.getString(3);
            filamentColor = cursor.getString(4);
            filamentLeft = cursor.getInt(5);
        }

        if (filamentName != null) {
            tv_detailFilamentName.setText(filamentName);
            tv_brand.setText(filamentBrand);
            tv_type.setText(filamentType);
            tv_color.setText(filamentColor);
            tv_filLeft.setText(String.valueOf(filamentLeft));

        } else {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFilament(View v) {
        int selectedFilamentId = getIntent().getIntExtra("selectedFilament", 0);
        String queryString = "DELETE FROM " + FILAMENT_TABLE + " WHERE " + FILAMENT_ID + " = " + selectedFilamentId;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(queryString);
        Intent i = new Intent(this, FilamentActivity.class);
        startActivity(i);
    }

    public void sendToFilaments(View v){
        Intent i = new Intent(this, FilamentActivity.class);
        startActivity(i);
    }
}