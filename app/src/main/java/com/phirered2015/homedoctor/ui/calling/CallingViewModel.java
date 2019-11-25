package com.phirered2015.homedoctor.ui.calling;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallingViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CallingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Calling page");
    }

    public LiveData<String> getText() { return mText; }
}
