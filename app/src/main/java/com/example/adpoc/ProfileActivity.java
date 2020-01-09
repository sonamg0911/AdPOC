package com.example.adpoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SharedPreferences pref;
    private EditText edtFirstName, edtLastName, edtEmail, edtContactNo;
    private Spinner spnPreference;
    private ImageView imgBack;
    private Integer selected_preference_position;
    private String selected_preference;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        setListeners();
        setPreferenceSpinnerData();
        pref = getApplicationContext().getSharedPreferences(Constants.USER_DETAILS, 0); // 0 - for private mode
        getDataIfPresent();
    }

    private void setListeners() {
        imgBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setPreferenceSpinnerData() {
        // Spinner click listener
        spnPreference.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        categories = new ArrayList<String>();
        categories.add("Not Selected");
        categories.add("Sports");
        categories.add("Entertainment");
        categories.add("Food");
        categories.add("Travel");

        // Creating adapter for spnPreference
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spnPreference
        spnPreference.setAdapter(dataAdapter);
    }

    private void initViews() {
        edtFirstName = (EditText) findViewById(R.id.signup_input_name);
        edtLastName = (EditText) findViewById(R.id.edt_last_name);
        edtEmail = (EditText) findViewById(R.id.signup_input_email);
        edtContactNo = (EditText) findViewById(R.id.signup_input_contact);
        spnPreference = (Spinner) findViewById(R.id.spn_ad_preference);
        imgBack = findViewById(R.id.img_back);
    }

    public void getDataIfPresent() {
        if(pref!=null) {

            if (pref.contains(Constants.NAME)) {
                edtFirstName.setText(pref.getString(Constants.NAME, ""));
            }
            if (pref.contains(Constants.EMAIL)) {
                edtEmail.setText(pref.getString(Constants.EMAIL, ""));

            }
            if (pref.contains(Constants.LASTNAME)) {
                edtLastName.setText(pref.getString(Constants.LASTNAME, ""));

            }
            if (pref.contains(Constants.CONTACT)) {
                edtContactNo.setText(pref.getString(Constants.CONTACT, ""));

            }
            if (pref.contains(Constants.SELECTED_AD_PREFERENCE_POSITION)) {
                spnPreference.setSelection(pref.getInt(Constants.SELECTED_AD_PREFERENCE_POSITION, 0));

            }
        }
    }

    public void saveData(View view) {
        String first_name_text = edtFirstName.getText().toString();
        String email_text = edtEmail.getText().toString();
        String last_name_text = edtLastName.getText().toString();
        String contact_text = edtContactNo.getText().toString();
        if(!TextUtils.isEmpty(first_name_text)
                &&!TextUtils.isEmpty(email_text)
                &&!TextUtils.isEmpty(last_name_text)
                &&!TextUtils.isEmpty(contact_text)
                &&selected_preference_position!=0){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Constants.NAME, first_name_text);
            editor.putString(Constants.EMAIL, email_text);
            editor.putString(Constants.LASTNAME, last_name_text);
            editor.putString(Constants.CONTACT, contact_text);
            editor.putInt(Constants.SELECTED_AD_PREFERENCE_POSITION,selected_preference_position);
            editor.putString(Constants.SELECTED_AD_PREFERENCE,selected_preference);
            editor.apply();
            Bundle extras = getIntent().getExtras();
            if(extras!=null && extras.getBoolean(Constants.ISFROMSPLASH) ){
                Intent i = new Intent(this,DashboardActivity.class);
                startActivity(i);
            }else{
                finish();
            }
        }else{
            Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_preference_position = position;
        selected_preference = categories.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
