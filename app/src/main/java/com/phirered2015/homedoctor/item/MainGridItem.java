package com.phirered2015.homedoctor.item;

import android.graphics.drawable.Drawable;

public class MainGridItem {
    private String pn;
    private int pp;
    Drawable grid_img;

    public MainGridItem(Drawable grid_img, String pn, int pp){
        this.grid_img = grid_img;
        this.pn = pn;
        this.pp = pp;
    }

    public void setImg(Drawable grid_img){ this.grid_img = grid_img; }

    public Drawable getImg(){ return grid_img; }

    public void setName(String pn){ this.pn = pn; }

    public String getName(){ return  pn; }

    public void setPrice(int pp){ this.pp = pp; }

    public int getPrice(){ return pp; }
}
