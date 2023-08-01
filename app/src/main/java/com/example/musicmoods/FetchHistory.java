package com.example.musicmoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.RequestQueue;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.activity.OnBackPressedCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class FetchHistory extends AppCompatActivity implements VolleyCallBack{
//    ConnectivityManager cm = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
//    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//    private TextView songView;
    private Song song;
    private SongService songService;
    private ArrayList<Song> recentlyPlayedTracks;

    private RecyclerView recyclerView;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private ArrayList<Item> recentItems;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_history);
        pb = findViewById(R.id.progressBar);
        songService = new SongService(getApplicationContext());
        recyclerView = findViewById(R.id.songHistoryRV);
        recyclerDataArrayList = new ArrayList<>();

//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (!isConnected) {
//            // do something
//            Toast.makeText(this, "You are not connected to the internet, please check your connection and try again", Toast.LENGTH_SHORT)
//                    .show();
//            startActivity(new Intent(FetchHistory.this,
//                    MainActivity.class));
//        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("Spotify", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long timeNow = System.currentTimeMillis() / 1000;

        //If the user is logged out, redirect to login page
        if(sharedPreferences.getString("token", "").equals("")){
            Toast.makeText(this, "You are currently logged out. Please login to continue", Toast.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(FetchHistory.this,
                    MainActivity.class));
        }
        //Redirect user to login page if token is expired
        else if(sharedPreferences.getLong("expiry", 0) <= timeNow){
            editor = getSharedPreferences(
                    getString(
                            R.string.shared_pref_key),
                    MODE_PRIVATE)
                    .edit();

            editor.putString("token",
                    "");
            editor.apply();
            Toast.makeText(this, "Session expired. Please connect to Spotify again", Toast.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(FetchHistory.this,
                    MainActivity.class));
        };
        pb.setVisibility(View.VISIBLE);
        songService.getRecentlyPlayedTracks(this);

//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
//                finish();
//            }
//        });
    }

    @Override
    public void onSuccess() {
        pb.setVisibility(View.INVISIBLE);
        recentlyPlayedTracks = songService.getSongs();
        recentItems = songService.getItems();

        String result = "";
        if(recentlyPlayedTracks != null && recentlyPlayedTracks.size() > 0){
            for(Song s: recentlyPlayedTracks){
                result += s.toString() + "\n";
            }
        }
        if(recentItems != null && recentItems.size()>0){
            for(Item it: recentItems){
                result += it.toString() + "\n";
            }
        }

        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        final PyObject pyObj = py.getModule("Predictor");

        for(Item it: recentItems){
            String trackId = it.getTrackId();
            PyObject obj = pyObj.callAttr("my_predict", trackId);
            String mood = obj.toString();
            it.setMood(mood);
//            Log.d("PyResponse", it.toString());
        }
        String itemResponse = getItemList();
        // added data from arraylist to adapter class.
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerDataArrayList,this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);

        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    String getItemList(){
        String result = "";
        for(Item it: recentItems){
            result += it.toString();
            RecyclerData rd = new RecyclerData(it.toString());
            recyclerDataArrayList.add(rd);
        }
        return result;
    }
}




