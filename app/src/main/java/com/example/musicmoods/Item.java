package com.example.musicmoods;

public class Item {
    Song song;
    String played_at;
    String mood;
//    double predictProbability;
    public Item(Song s, String pa){
        this.song = s;
        pa = pa.substring(0, pa.length() - 5);
        pa = pa.replace("T", " ");
        this.played_at = pa;
    }

    public String getTrackId(){
        return this.song.getId();
    }
    public void setMood(String mood) {
        this.mood = mood;
    }

//    public void setPredictProbability(double prob){
//        this.predictProbability = prob;
//    }

    @Override
    public String toString(){
        String result = song.getName() + "\n Streamed on: " + played_at + "\n Predicted Mood: " + mood + "\n";
        return result;
    }
}
