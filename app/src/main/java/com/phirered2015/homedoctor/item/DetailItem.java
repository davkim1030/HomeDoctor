package com.phirered2015.homedoctor.item;

import com.google.firebase.storage.StorageReference;

public class DetailItem {
    private String name;
    private String price;
    private String description;
    private StorageReference top_img;
    private StorageReference desc_img;

    public DetailItem(StorageReference top_img, String name, String price, String description, StorageReference desc_img){
        this.top_img = top_img;
        this.name = name;
        this.price = price;
        this.description = description;
        this.desc_img = desc_img;
    }

    public void settopImg(StorageReference top_img) { this.top_img = top_img; }

    public StorageReference gettopImg() { return top_img; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setPrice(String price) { this.price = price; }

    public String getPrice() { return price; }

    public void setDescription() { this.description = description; }

    public String getDescription() { return description; }

    public void setdescImg(StorageReference desc_img) { this.desc_img = desc_img; }

    public StorageReference getDesc_img() { return desc_img; }


}
