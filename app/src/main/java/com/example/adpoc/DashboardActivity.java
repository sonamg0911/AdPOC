package com.example.adpoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnProfile,btnSummary,btnViewVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initViews();
        initListeners();
    }

    private void initListeners() {
        btnProfile.setOnClickListener(this);
        btnSummary.setOnClickListener(this);
        btnViewVideos.setOnClickListener(this);
    }

    private void initViews() {

        btnProfile = findViewById(R.id.btn_profile);
        btnViewVideos = findViewById(R.id.btn_view_videos);
        btnSummary = findViewById(R.id.btn_summary);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profile:
                Intent profileIntent = new Intent(this,ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.btn_view_videos:
                Intent videoIntent = new Intent(this,VideoActivity.class);
                startActivity(videoIntent);
                break;
            case R.id.btn_summary:
                Intent summaryIntent = new Intent(this,SummaryActivity.class);
                startActivity(summaryIntent);
                break;


        }
    }
}
