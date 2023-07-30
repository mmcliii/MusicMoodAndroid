package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton python0button = findViewById(R.id.button0python);
        python0button.setOnClickListener(view -> pythonTest0());
    }

    protected void pythonTest0(){
        Intent secondActivityIntent = new Intent(this, SpotifyLogin.class);
        startActivity(secondActivityIntent);
    }
}