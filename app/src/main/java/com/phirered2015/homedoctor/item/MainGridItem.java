package com.phirered2015.homedoctor.item;


import com.google.firebase.storage.StorageReference;

public class MainGridItem {
    private String pn;
    private String pp;
    private StorageReference grid_img;

    public MainGridItem(StorageReference grid_img, String pn, String pp){
        this.grid_img = grid_img;
        this.pn = pn;
        this.pp = pp;
    }

    public void setImg(StorageReference grid_img){ this.grid_img = grid_img; }

    public StorageReference getImg(){ return grid_img; }

    public void setName(String pn){ this.pn = pn; }

    public String getName(){ return  pn; }

    public void setPrice(String pp){ this.pp = pp; }

    public String getPrice(){ return pp; }
}
