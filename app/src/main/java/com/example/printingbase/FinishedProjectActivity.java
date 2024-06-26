package com.example.printingbase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class FinishedProjectActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    ListView lv_finishedProjects;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter finishedProjectArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finished_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv_finishedProjects = findViewById(R.id.lv_finishedProjects);
        dataBaseHelper = new DataBaseHelper(FinishedProjectActivity.this);

        showFinishedProjectsOnList(dataBaseHelper);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

        lv_finishedProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectModel selectedProject = (ProjectModel) parent.getItemAtPosition(position);
                Intent i = new Intent(FinishedProjectActivity.this, DetailedFinishedProjectActivity.class);

                int itemId = 0;
                if (selectedProject != null) {
                    itemId = selectedProject.getId();
                }

                i.putExtra("selectedFinishedProject", itemId);
                startActivity(i);
            }
        });
    }

    public void showFinishedProjectsOnList(DataBaseHelper dataBaseHelper){
        finishedProjectArrayAdapter = new ArrayAdapter<ProjectModel>(FinishedProjectActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getFinishedProjects()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.rgb(247, 238, 221));
                view.setPadding(10, 10, 0, 15);
                view.setBackgroundColor(Color.rgb(91, 181, 199));

                return view;
            }
        };
        lv_finishedProjects.setAdapter(finishedProjectArrayAdapter);
//        Toast.makeText(FinishedProjectActivity.this, "success", Toast.LENGTH_SHORT).show();
    }

    public void sendToMenu(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    public void refreshFinishedProjects(View v) {
        showFinishedProjectsOnList(dataBaseHelper);
    }
}