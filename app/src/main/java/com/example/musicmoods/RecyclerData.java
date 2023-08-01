package com.example.musicmoods;

import androidx.cardview.widget.CardView;

public class RecyclerData {

    private String info;
    private CardView card;
//    private int imgid;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
//
//    public int getImgid() {
//        return imgid;
//    }
//
//    public void setImgid(int imgid) {
//        this.imgid = imgid;
//    }

    public RecyclerData(String info) {
        this.info = info;
//        this.imgid = imgid;
    }
}
