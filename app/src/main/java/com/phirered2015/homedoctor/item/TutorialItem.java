package com.phirered2015.homedoctor.item;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class TutorialItem {
    private Drawable imgDesc;
    private String txtDesc;
    private Class activity = null;

    public TutorialItem(Drawable img, String txt){
        imgDesc = img;
        txtDesc = txt;
    }

    public TutorialItem(Drawable img, String txt, Class activity){
        imgDesc = img;
        txtDesc = txt;
        this.activity = activity;
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

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
