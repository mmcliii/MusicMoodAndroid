package com.example.musicmoods;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.crypto.AEADBadTagException;


public class SongService {
    private ArrayList<Song> songs;
    private SharedPreferences sharedPreferences;
    private MySingleton queue;
    private ArrayList<Item> items;

    public SongService(Context context){
            sharedPreferences = context.getSharedPreferences("Spotify", Context.MODE_PRIVATE);
            queue = MySingleton.getInstance(context);
            this.songs = new ArrayList<>();
            this.items = new ArrayList<>();
        }

        public ArrayList<Song> getSongs(){
//            ArrayList<Song> mySongs = new ArrayList<>();
//            for(Song s: songs){
//                mySongs.add(s);
//            }
            Log.d("getSongs", songs.toString());
            return songs;
        }
        public void setSongs(ArrayList<Song> songs){
           this.songs = songs;
        }
        public ArrayList<Item> getItems(){
            Log.d("getItems", items.toString());
            return items;
        }

        public void getRecentlyPlayedTracks(final VolleyCallBack callBack){
            String endpoint = "https://api.spotify.com/v1/me/player/recently-played?limit=50";
//            String endpoint = "https://api.spotify.com/v1/me/player/currently-playing";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, endpoint, null, response -> {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.optJSONArray("items");
                            for (int n = 0; n < jsonArray.length(); n++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(n);
                                    JSONObject object1 = object.optJSONObject("track");
                                    Song song = gson.fromJson(object1.toString(), Song.class);
                                    Song song1 = new Song(song.getId(), song.getName());
                                    songs.add(song1);
//                                    Log.d("thiSong", song.toString());
                                    String played_at = object.optString("played_at");
                                    Item item = new Item(song1, played_at);
                                    items.add(item);
//                                    Log.d("thisItem", item.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }
                            Log.d("songs", "" + songs.get(0).toString());
                            Log.d("items", "" + items.get(0).toString());
//                            Log.d("items", items.toString());
                        setSongs(songs);
                        callBack.onSuccess();
                    }, error -> {
                        // TODO: Handle error

                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String token = sharedPreferences.getString("token", "");
                    String auth = "Bearer " + token;
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            queue.addToRequestQueue(jsonObjectRequest);
        }

        public void addSongToLibrary(Song song) {
            JSONObject payload = preparePutPayload(song);
            JsonObjectRequest jsonObjectRequest = prepareSongLibraryRequest(payload);
            queue.addToRequestQueue(jsonObjectRequest);
        }

        private JsonObjectRequest prepareSongLibraryRequest(JSONObject payload) {
            return new JsonObjectRequest(Request.Method.PUT, "https://api.spotify.com/v1/me/tracks", payload, response -> {
            }, error -> {
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String token = sharedPreferences.getString("token", "");
                    String auth = "Bearer " + token;
                    headers.put("Authorization", auth);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
        }

        private JSONObject preparePutPayload(Song song) {
            JSONArray idarray = new JSONArray();
            idarray.put(song.getId());
            JSONObject ids = new JSONObject();
            try {
                ids.put("ids", idarray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ids;
        }
}
