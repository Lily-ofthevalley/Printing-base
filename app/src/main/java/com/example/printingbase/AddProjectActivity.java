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

public class AddProjectActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addProjectFinal;
    EditText et_addProjectName, et_filamentAmountNeeded, et_printTime, et_filaments;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addProjectFinal = findViewById(R.id.btn_addProjectFinal);
        et_addProjectName = findViewById(R.id.et_addProjectName);
        et_filamentAmountNeeded = findViewById(R.id.et_filamentAmountNeeded);
        et_printTime = findViewById(R.id.et_printTime);
        et_filaments = findViewById(R.id.et_filaments);
        dataBaseHelper = new DataBaseHelper(AddProjectActivity.this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        //button listeners for the add and view all buttons
        btn_addProjectFinal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ProjectModel projectModel;

                try {
                    projectModel = new ProjectModel(-1,
                            et_addProjectName.getText().toString(),
                            et_filaments.getText().toString(),
                            Integer.parseInt(et_filamentAmountNeeded.getText().toString()),
                            Integer.parseInt(et_printTime.getText().toString()));
//                    Toast.makeText(AddProjectActivity.this, "ja", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddProjectActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    projectModel = new ProjectModel(-1, "error", "error", 0, 0);
                }

                try {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(AddProjectActivity.this);

                    boolean success = dataBaseHelper.addOneProject(projectModel);

//                    Toast.makeText(AddProjectActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddProjectActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(AddProjectActivity.this, ProjectActivity.class);
                startActivity(i);
            }
        });
    }

    public void sendToMain(View v){
        Intent i = new Intent(this, ProjectActivity.class);
        startActivity(i);
    }
}