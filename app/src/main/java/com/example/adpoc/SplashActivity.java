package com.example.adpoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int TIME_OUT = 4000; //Time to launch the another activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(!TextUtils.isEmpty(getApplicationContext().getSharedPreferences(Constants.USER_DETAILS, 0).getString(Constants.NAME,""))) {
                    i = new Intent(SplashActivity.this, DashboardActivity.class);
                }else{
                    i = new Intent(SplashActivity.this, ProfileActivity.class);
                    i.putExtra(Constants.ISFROMSPLASH,true);
                }
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
