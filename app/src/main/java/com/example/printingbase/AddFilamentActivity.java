package com.example.printingbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddFilamentActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addFilamentFinal;
    EditText et_filamentName, et_filamentBrand, et_filamentType, et_filamentColor, et_filamentAmount;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_filament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addFilamentFinal = findViewById(R.id.btn_addFilamentFinal);
        et_filamentName = findViewById(R.id.et_filamentName);
        et_filamentBrand = findViewById(R.id.et_filamentBrand);
        et_filamentType = findViewById(R.id.et_filamentType);
        et_filamentColor = findViewById(R.id.et_filamentColor);
        et_filamentAmount = findViewById(R.id.et_filamentAmount);
        dataBaseHelper = new DataBaseHelper(AddFilamentActivity.this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        //button listeners for the add and view all buttons
        btn_addFilamentFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilamentModel filamentModel;

                try {
                    filamentModel = new FilamentModel(-1,
                            et_filamentName.getText().toString(),
                            et_filamentBrand.getText().toString(),
                            et_filamentType.getText().toString(),
                            et_filamentColor.getText().toString(),
                            Integer.parseInt(et_filamentAmount.getText().toString()));
                } catch (Exception e) {
                    Toast.makeText(AddFilamentActivity.this, "Error creating filament", Toast.LENGTH_SHORT).show();
                    filamentModel = new FilamentModel(-1,
                            "error",
                            "error",
                            "error",
                            "error",
                            0);
                }

                try {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(AddFilamentActivity.this);

                    boolean success = dataBaseHelper.addOneFilament(filamentModel);

//                    Toast.makeText(AddFilamentActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddFilamentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(AddFilamentActivity.this, FilamentActivity.class);
                startActivity(i);
            }
        });
    }

    public void sendToFilaments(View v){
        Intent i = new Intent(this, FilamentActivity.class);
        startActivity(i);
    }
}
