package com.phirered2015.homedoctor.ui.delivery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.phirered2015.homedoctor.R;


public class DeliveryFragment extends Fragment {
    private DeliveryViewModel deliveryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       deliveryViewModel =
                ViewModelProviders.of(this).get(DeliveryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delivery, container, false);
        final TextView textView = root.findViewById(R.id.text_delivery);
        deliveryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
