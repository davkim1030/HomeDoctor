package com.phirered2015.homedoctor.item;

import android.graphics.drawable.Drawable;

public class TutorialItem {
    private Drawable imgDesc;
    private String txtDesc;

    public TutorialItem(Drawable img, String txt){
        imgDesc = img;
        txtDesc = txt;
    }

    public Drawable getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(Drawable imgDesc) {
        this.imgDesc = imgDesc;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }
}
