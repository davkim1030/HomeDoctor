package com.phirered2015.homedoctor.ui.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeliveryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DeliveryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Delivery page");
    }

    public LiveData<String> getText() { return mText; }
}
