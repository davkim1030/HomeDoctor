package com.phirered2015.homedoctor.ui.mypage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MypageViewModel extends ViewModel {
    private MutableLiveData<String> vwCtrl;
    public MypageViewModel() {
        vwCtrl = new MutableLiveData<>();
        vwCtrl.setValue("");
    }
     @Override public void onCleared(){
        return;
     }
}
