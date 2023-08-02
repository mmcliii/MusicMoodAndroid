package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.google.android.material.button.MaterialButton;
import androidx.activity.OnBackPressedCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {
//    ConnectivityManager cm = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
//    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (!isConnected) {
//            // do something
//            Toast.makeText(this, "You are not connected to the internet, please check your connection and try again", Toast.LENGTH_SHORT)
//                    .show();
//            startActivity(new Intent(DashboardActivity.this,
//                    MainActivity.class));
//        }
        MaterialButton logout_button = findViewById(R.id.logout_button1);
        logout_button.setOnClickListener(view -> logout());
        SharedPreferences sharedPreferences = this.getSharedPreferences("Spotify", MODE_PRIVATE);
        //If the user is not logged in, redirect to sign in page
        if(sharedPreferences.getString("token", "").equals("")){
            startActivity(new Intent(DashboardActivity.this,
                    MainActivity.class));
            finish();
        }
        MaterialButton moodButton = findViewById(R.id.moodButton);
        moodButton.setOnClickListener(view -> viewMoods());

//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
//                if(sharedPreferences.getString("token", "").equals("")){
//                    startActivity(new Intent(DashboardActivity.this,
//                            MainActivity.class));
//                    finish();
//                }
//            }
//        });
    }
    protected void logout(){
        Intent secondActivityIntent = new Intent(this, SpotifyLogout.class);
        startActivity(secondActivityIntent);
    }
    protected void viewMoods(){
        Intent moodIntent = new Intent(this, FetchHistory.class);
        startActivity(moodIntent);
    }


}