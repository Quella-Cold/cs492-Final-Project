package com.example.chooseyourmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chooseyourmeal.data.CurrentLocation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RandomFragment extends Fragment {

    private ImageView mImageView;
    private TextView  mNameTV;
    private TextView  mHourTV;
    private TextView  mDistanceTV;
    private Button    mDirectionBT;
    private Button    mNewBT;
    private FloatingActionButton mFavBT;

    private CurrentLocation mCurrentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
        return view;
    }

    public void updateLocation() {
        mCurrentLocation = ((MainActivity) getActivity()).getCurrentLocation();
    }


}
