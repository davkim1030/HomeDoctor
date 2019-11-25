package com.phirered2015.homedoctor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.phirered2015.homedoctor.R;

public class HomeFragment extends Fragment {
    private HomeViewModel hV;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        hV = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textview = root.findViewById(R.id.text_home);

        hV.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textview.setText(s);
            }
        });

        return root;
    }
}
