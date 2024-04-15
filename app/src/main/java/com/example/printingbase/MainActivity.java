package com.example.printingbase;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addProject, btn_addProjectFinal;
    EditText et_addProjectName, et_filamentAmountNeeded, et_printTime;
    Spinner sp_filaments;
    RecyclerView rv_projects;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addProject = findViewById(R.id.btn_addProject);
        btn_addProjectFinal = findViewById(R.id.btn_addProjectFinal);
        et_addProjectName = findViewById(R.id.et_addProjectName);
        et_filamentAmountNeeded = findViewById(R.id.et_filamentAmountNeeded);
        et_printTime = findViewById(R.id.et_printTime);
        sp_filaments = findViewById(R.id.sp_filaments);
        rv_projects = findViewById(R.id.rv_projects);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        View btnAddProject = btn_addProject;
        btnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAddProject();
            }
        });

    }
    public void sendToMenu(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    private void viewAddProject(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_project);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        dialog.show();
    }
}