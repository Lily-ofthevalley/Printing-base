package com.example.printingbase;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PlasticActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addFilament, btn_addProjectFinal;
    EditText et_filamentName, et_filamentAmountNeeded, et_filamentType, et_filamentColor, et_filamentAmount;
    RecyclerView rv_filaments;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plastic);
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
        rv_filaments = findViewById(R.id.rv_filaments);
        dataBaseHelper = new DataBaseHelper(PlasticActivity.this);

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
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_filament);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        dialog.show();
    }
}