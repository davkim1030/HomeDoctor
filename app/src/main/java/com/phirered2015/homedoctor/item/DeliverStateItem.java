package com.phirered2015.homedoctor.item;

import android.graphics.drawable.Drawable;

public class DeliverStateItem {
    private String name, state;
    private int price;
    Drawable img;

    public DeliverStateItem(String name, String state, int price, Drawable img){
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

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
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
