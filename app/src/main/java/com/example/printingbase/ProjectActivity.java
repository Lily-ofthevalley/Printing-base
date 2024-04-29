package com.example.printingbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class ProjectActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_addProject;
    ListView lv_projects;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter projectArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addProject = findViewById(R.id.btn_addProject);
        lv_projects = findViewById(R.id.lv_projects);
        dataBaseHelper = new DataBaseHelper(ProjectActivity.this);

        showProjectsOnList(dataBaseHelper);

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

        lv_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectModel selectedProject = (ProjectModel) parent.getItemAtPosition(position);
                Intent i = new Intent(ProjectActivity.this, DetailedProjectActivity.class);

                int itemId = 0;
                if(selectedProject != null) {
                    itemId = selectedProject.getId();
                }

                i.putExtra("selectedProject", itemId);

                startActivity(i);
            }
        });

    }

    public void sendToMenu(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    private void viewAddProject(){
        showProjectsOnList(dataBaseHelper);
        Intent i = new Intent(this, AddProjectActivity.class);
        startActivity(i);
    }

    public void showProjectsOnList(DataBaseHelper dataBaseHelper){
        projectArrayAdapter = new ArrayAdapter<ProjectModel>(ProjectActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getProjects());
        lv_projects.setAdapter(projectArrayAdapter);
    }
}