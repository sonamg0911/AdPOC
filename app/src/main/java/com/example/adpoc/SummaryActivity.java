package com.example.adpoc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private TextView tvSportAdCount,tvEntertainmentAdCount,tvFoodAdCount,tvTravelAdCount,tvSportPoint,tvFoodPoint,tvEntertainmentPoint,tvTravelPoint,tvTotalPoint;
    private TextView tvFoodVideoCount,tvTravelVideoCount,tvSportVideoCount,tvEntertainmentVideoCount;
    private ImageView imgBack;
    private LinearLayout llSports,llEntertainment,llFood,llTravel;
    private Integer totalPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        pref = getApplicationContext().getSharedPreferences(Constants.USER_DETAILS, 0); // 0 - for private mode
        initViews();
        setListeners();
        totalPoints = (pref.getInt(Constants.TRAVEL_AD_COUNT,0)*5)
                +(pref.getInt(Constants.FOOD_AD_COUNT,0)*5)
                +(pref.getInt(Constants.SPORT_AD_COUNT,0)*5)
                +(pref.getInt(Constants.ENTERTAINMENT_AD_COUNT,0)*5);
        setData();

    }

    private void setListeners() {
        imgBack.setOnClickListener(v -> finish());
    }

    private void setData() {
        tvSportAdCount.setText(""+pref.getInt(Constants.SPORT_AD_COUNT,0));
        tvFoodAdCount.setText(""+pref.getInt(Constants.FOOD_AD_COUNT,0));
        tvTravelAdCount.setText(""+pref.getInt(Constants.TRAVEL_AD_COUNT,0));
        tvEntertainmentAdCount.setText(""+pref.getInt(Constants.ENTERTAINMENT_AD_COUNT,0));
        tvSportVideoCount.setText(""+pref.getInt(Constants.SPORT_VIDEO_COUNT,0));
        tvFoodVideoCount.setText(""+pref.getInt(Constants.FOOD_VIDEO_COUNT,0));
        tvTravelVideoCount.setText(""+pref.getInt(Constants.TRAVEL_VIDEO_COUNT,0));
        tvEntertainmentVideoCount.setText(""+pref.getInt(Constants.ENTERTAINMENT_VIDEO_COUNT,0));
        tvSportPoint.setText(""+pref.getInt(Constants.SPORT_AD_COUNT,0)*5);
        tvFoodPoint.setText(""+pref.getInt(Constants.FOOD_AD_COUNT,0)*5);
        tvTravelPoint.setText(""+pref.getInt(Constants.TRAVEL_AD_COUNT,0)*5);
        tvEntertainmentPoint.setText(""+pref.getInt(Constants.ENTERTAINMENT_AD_COUNT,0)*5);
        tvTotalPoint.setText(""+totalPoints);
    }

    private void initViews() {
        imgBack = findViewById(R.id.img_back);

        tvSportAdCount = findViewById(R.id.tv_sports_ad_count);
        tvEntertainmentAdCount = findViewById(R.id.tv_entertainment_ad_counts);
        tvFoodAdCount = findViewById(R.id.tv_food_ad_counts);
        tvTravelAdCount = findViewById(R.id.tv_travel_ad_counts);

        tvSportVideoCount = findViewById(R.id.tv_sports_video_count);
        tvEntertainmentVideoCount = findViewById(R.id.tv_entertainment_video_count);
        tvFoodVideoCount = findViewById(R.id.tv_food_video_count);
        tvTravelVideoCount = findViewById(R.id.tv_travel_video_count);

        tvSportPoint = findViewById(R.id.tv_sports_points);
        tvEntertainmentPoint = findViewById(R.id.tv_entertainment_points);
        tvFoodPoint = findViewById(R.id.tv_food_points);
        tvTravelPoint = findViewById(R.id.tv_travel_points);

        tvTotalPoint = findViewById(R.id.tv_total_points);

        llEntertainment = findViewById(R.id.ll_entertainment);
        llTravel = findViewById(R.id.ll_travel);
        llFood = findViewById(R.id.ll_food);
        llSports = findViewById(R.id.ll_sports);
    }
}
