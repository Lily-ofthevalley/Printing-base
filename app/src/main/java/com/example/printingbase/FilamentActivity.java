package com.example.printingbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilamentActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addFilament, btn_addProjectFinal;
    EditText et_filamentName, et_filamentAmountNeeded, et_filamentType, et_filamentColor, et_filamentAmount;
    ListView lv_filaments;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter filamentArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addFilament = findViewById(R.id.btn_addFilament);
        btn_addProjectFinal = findViewById(R.id.btn_addProjectFinal);
        et_filamentName = findViewById(R.id.et_filamentName);
        et_filamentAmountNeeded = findViewById(R.id.et_filamentAmountNeeded);
        et_filamentType = findViewById(R.id.et_filamentType);
        et_filamentColor = findViewById(R.id.et_filamentColor);
        et_filamentAmount = findViewById(R.id.et_filamentAmount);
        lv_filaments = findViewById(R.id.lv_filament);
        dataBaseHelper = new DataBaseHelper(FilamentActivity.this);

        showFilamentsOnList(dataBaseHelper);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        View btnAddFilament = btn_addFilament;
        btnAddFilament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAddFilament();
            }
        });
    }

    private void viewAddFilament(){
        showFilamentsOnList(dataBaseHelper);
        Intent i = new Intent(this, AddFilamentActivity.class);
        startActivity(i);
    }

    public void sendToMenu(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    public void showFilamentsOnList(DataBaseHelper dataBaseHelper){
        filamentArrayAdapter = new ArrayAdapter<FilamentModel>(FilamentActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getFilaments());
        lv_filaments.setAdapter(filamentArrayAdapter);
        Toast.makeText(FilamentActivity.this, "success", Toast.LENGTH_SHORT).show();
    }
}