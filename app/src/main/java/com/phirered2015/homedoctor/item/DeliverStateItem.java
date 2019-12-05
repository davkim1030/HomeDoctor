package com.phirered2015.homedoctor.item;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class DeliverStateItem{
    private String name, state;
    private int price;
    StorageReference img;
    public DeliverStateItem(String name, String state, int price, StorageReference img){
        this.name = name;
        this.state = state;
        this.price = price;
        this.img = img;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public StorageReference getImg() {
        return img;
    }

    public void setImg(StorageReference img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
