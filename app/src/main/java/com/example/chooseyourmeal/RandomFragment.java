package com.example.chooseyourmeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chooseyourmeal.data.CurrentLocation;
import com.example.chooseyourmeal.data.LoadMealArgs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RandomFragment extends Fragment {

    private CurrentLocation mCurrentLocation;
    private Spinner mSpinner;
    private Button mButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
        mSpinner = view.findViewById(R.id.sp_random);
        mButton = view.findViewById(R.id.bt_random_new);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
                String s_radius = String.valueOf(mSpinner.getSelectedItem());
                int radius = Integer.parseInt(s_radius);
                Log.d("new", s_radius+" "+ mCurrentLocation.lat + " " + mCurrentLocation.lon);
                LoadMealArgs args = new LoadMealArgs();
                args.lat = Float.parseFloat(mCurrentLocation.lat);
                args.lng = Float.parseFloat(mCurrentLocation.lon);
                args.radius= radius;
                args.type= "restaurants";
                Intent intent = new Intent(getContext(), RandomActivity.class);
                intent.putExtra("Information", args);
                startActivity(intent);
            }
        });
        return view;
    }

    public void updateLocation() {
        mCurrentLocation = ((MainActivity) getActivity()).getCurrentLocation();
    }

}
