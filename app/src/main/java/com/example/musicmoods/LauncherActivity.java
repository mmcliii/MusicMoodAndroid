package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //If the user is not logged in, redirect to sign in page
        SharedPreferences sharedPreferences = this.getSharedPreferences("Spotify", MODE_PRIVATE);
        if(sharedPreferences.getString("token", "").equals("")){
            startActivity(new Intent(LauncherActivity.this,
                    MainActivity.class));
        }
        //else redirect to their dashboard
        else{
            goToDashboard();
        }

        MaterialButton button = findViewById(R.id.dashboard_button);
        button.setOnClickListener(view -> goToDashboard());
    }
    protected void goToDashboard(){
        startActivity(new Intent(LauncherActivity.this,
                DashboardActivity.class));
    }
}