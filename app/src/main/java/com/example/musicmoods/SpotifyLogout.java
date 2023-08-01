package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.spotify.sdk.android.auth.*;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SpotifyLogout extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private SharedPreferences.Editor editor;
//    ConnectivityManager cm = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
//    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Spotify", MODE_PRIVATE);

//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (!isConnected) {
//            // do something
//            Toast.makeText(this, "You are not connected to the internet, please check your connection and try again", Toast.LENGTH_SHORT)
//                    .show();
//            startActivity(new Intent(SpotifyLogout.this,
//                    MainActivity.class));
//        }
        if(sharedPreferences.getString("token", "").equals("")){
            Toast.makeText(this, "You have already logged out. Please sign in to continue", Toast.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(SpotifyLogout.this,
                    MainActivity.class));
        }
        editor = getSharedPreferences(
                getString(
                        R.string.shared_pref_key),
                MODE_PRIVATE)
                .edit();

        editor.putString("token",
                "");
        editor.apply();

        startActivity(new Intent(SpotifyLogout.this,
                MainActivity.class));

//        MaterialButton logOut = findViewById(R.id.proceedLogOut);
//        logOut.setOnClickListener(view -> goToUri("https://www.spotify.com/us/account/apps"));
        String CLIENT_ID = getResources().getString(R.string.CLIENT_ID);

        String REDIRECT_URI = getResources().getString(R.string.REDIRECT_URI);

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-recently-played, user-read-currently-playing"});
        builder.setShowDialog(true);
        AuthorizationRequest request = builder.build();

//        AuthorizationClient.openLoginInBrowser(this, request);


    }
//    protected void goToUri(String uriString){
//        Uri webpage = Uri.parse(uriString);
//        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
//        startActivity(webIntent);
//    }
}