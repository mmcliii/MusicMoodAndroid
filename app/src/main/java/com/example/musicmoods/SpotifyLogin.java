package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;
import com.spotify.sdk.android.auth.*;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.time.Instant;

public class SpotifyLogin extends AppCompatActivity {
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
    private static final int REQUEST_CODE = 1337;
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String CLIENT_ID = getResources().getString(R.string.CLIENT_ID);
        final String REDIRECT_URI = getResources().getString(R.string.REDIRECT_URI);

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
//        AuthorizationRequest.Builder builder =
//                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.CODE, REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-recently-played,user-read-currently-playing"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode,
                intent);

        // Check if result comes from the our Spotify login activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response
                    = AuthorizationClient.getResponse(
                    resultCode, intent);

            switch (response.getType()) {
                // If the response was successful and contains our auth token
                case TOKEN:
                    long currentTimeMillis = System.currentTimeMillis();
                    long currentTimeSecs = currentTimeMillis / 1000;
                    long expiry = currentTimeSecs + 3600;
                    // we'll store the auth token in shared preferences
                    editor = getSharedPreferences(
                            getString(
                                    R.string.shared_pref_key),
                            MODE_PRIVATE)
                            .edit();

                    editor.putString("token",
                            response.getAccessToken());
                    editor.putLong("expiry", expiry);
                    editor.apply();
                    Log.d("printExpiry", "" + getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE).getLong("expiry", 0));

                    // We'll make a toast in our app to confirm successful auth
                    Toast.makeText(this, "Auth successful", Toast.LENGTH_SHORT)
                            .show();

                    // We'll start the main activity after auth is successful
                    startActivity(new Intent(SpotifyLogin.this,
                            DashboardActivity.class));

                    break;

                // Auth returned an error
                case ERROR:
                    Log.d("LoginActivity", response.getError());
                    Toast.makeText(this, "Could not connect to Spotify. Please try again", Toast.LENGTH_SHORT)
                            .show();

                    // We'll start the main activity after auth is successful
                    startActivity(new Intent(SpotifyLogin.this,
                            MainActivity.class));
                    break;

                default:
                    Log.d("LoginActivity", response.toString());
                    // We'll make a toast in our app to confirm successful auth
                    Toast.makeText(this, "Login cancelled", Toast.LENGTH_SHORT)
                            .show();

                    // We'll start the main activity after auth is successful
                    startActivity(new Intent(SpotifyLogin.this,
                            MainActivity.class));

            }
        }
    }
}
