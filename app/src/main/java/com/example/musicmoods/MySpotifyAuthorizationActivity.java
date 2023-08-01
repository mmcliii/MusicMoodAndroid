package com.example.musicmoods;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySpotifyAuthorizationActivity extends AppCompatActivity{
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        msharedPreferences = this.getSharedPreferences("Spotify", 0);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthorizationResponse response = AuthorizationResponse.fromUri(uri);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    editor = getSharedPreferences(
                            getString(
                                    R.string.shared_pref_key),
                            0)
                            .edit();

                    editor.putString("token",
                            response.getAccessToken());
//                    editor.putString("loggedIn", "false");
                    editor.apply();
                    // We'll make a toast in our app to confirm successful auth
                    Toast.makeText(this, "Auth successful", Toast.LENGTH_SHORT)
                            .show();
                    // We'll start the main activity after auth is successful
                    startActivity(new Intent(MySpotifyAuthorizationActivity.this,
                            MainActivity.class));
                    break;
                case CODE:
                    // Handle successful response
                    editor = getSharedPreferences(
                            getString(
                                    R.string.shared_pref_key),
                            MODE_PRIVATE)
                            .edit();

                    editor.putString("code", response.getCode());
                    editor.putString("loggedIn", "true");
                    editor.apply();
                    // We'll make a toast in our app to confirm successful auth
                    Toast.makeText(this, "Auth successful", Toast.LENGTH_SHORT)
                            .show();
//                    getAccessToken();
//                    Log.d("tokenSuccess","Successfully fetched access token");
                    // We'll start the main activity after auth is successful
                    startActivity(new Intent(MySpotifyAuthorizationActivity.this,
                            MainActivity.class));
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.d("SpotifyLogin", response.getError());
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    startActivity(new Intent(MySpotifyAuthorizationActivity.this,
                            MainActivity.class));
            }
        }
    }


}
