package com.example.musicmoods;

public class Song {
    String id;
    String name;

    public Song(String id, String name){
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
