package com.example.musicmoods;

public class Song {
    String id;
    String name;

    public Song(String id, String name){
        this.id = id;
        this.name = name;
    }
    public Song(){

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        String songString = this.id + " " + this.name;
        return songString;
    }
}
