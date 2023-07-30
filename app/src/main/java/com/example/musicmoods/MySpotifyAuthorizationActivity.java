package com.example.musicmoods;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class MySpotifyAuthorizationActivity extends AppCompatActivity{
    private SharedPreferences.Editor editor;
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
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
                            MODE_PRIVATE)
                            .edit();

                    editor.putString("token",
                            response.getAccessToken());
                    editor.apply();
                    // We'll make a toast in our app to confirm successful auth
                    Toast.makeText(this, "Auth successful", Toast.LENGTH_SHORT)
                            .show();
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
