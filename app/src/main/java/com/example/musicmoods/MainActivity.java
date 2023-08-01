package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import androidx.activity.OnBackPressedCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> spotifyLogin());

//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
//                finish();
//            }
//        });
    }

    protected void spotifyLogin(){
        Intent secondActivityIntent = new Intent(this, SpotifyLogin.class);
        startActivity(secondActivityIntent);
    }


}