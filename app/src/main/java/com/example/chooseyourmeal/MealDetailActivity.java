package com.example.chooseyourmeal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.example.chooseyourmeal.data.LoadMealArgs;
import com.example.chooseyourmeal.data.MealListItem;
import com.example.chooseyourmeal.data.MealListItemsBundle;
import com.example.chooseyourmeal.utils.BuildPlaceApiRequestUrl;
import com.example.chooseyourmeal.utils.MapUtil;
import com.example.chooseyourmeal.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MealDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mNameTV;
    private TextView mOpenTV;
    private TextView mRatingTV;
    private TextView mAddressTV;
    private Button mNavBT;
    private Button mFavBT;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;
    private LinearLayout mll;

    private Boolean esxist;
    private MealListItem mResult;
    private MealListViewModel mViewModel;
    private Toast mToast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_activity);
        esxist=true;
        mImageView = findViewById(R.id.iv_random);
        mNameTV = findViewById(R.id.tv_random_name);
        mOpenTV = findViewById(R.id.tv_random_hour);
        mRatingTV = findViewById(R.id.tv_random_rate);
        mAddressTV = findViewById(R.id.tv_random_add);
        mNavBT = findViewById(R.id.bt_random_direction);
        mFavBT = findViewById(R.id.bt_random_fav);
        mLoadingIndicatorPB = findViewById(R.id.pd_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_error_message);
        mll = findViewById(R.id.ly_random);

        mViewModel=new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MealListViewModel.class);
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        mErrorMessageTV.setVisibility(View.INVISIBLE);
        mll.setVisibility(View.INVISIBLE);

        mNavBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLocationOnMap();
            }
        });

        mFavBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Information")) {
            mResult = (MealListItem) intent.getSerializableExtra(
                    "Information"
            );
        }

        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (mResult != null){
            mll.setVisibility(View.VISIBLE);
            mNameTV.setText(mResult.mealName);
            if(TextUtils.equals(mResult.open, "true")) {
                mOpenTV.setText("Yes");
            } else if(TextUtils.equals(mResult.open, "false")) {
                mOpenTV.setText("No");
            }else{
                mOpenTV.setText("Unknown");
            }

            String rate = String.valueOf(mResult.rating);
            mRatingTV.setText(rate);
            mAddressTV.setText(mResult.address);
            mViewModel.getFavMealByAddress(mResult.address).observe(this, new Observer<MealListItem>() {
                @Override
                public void onChanged(MealListItem item) {
                    if(item != null){
                        esxist=true;
                        Log.d("Inserting=","Already Exists");
                    }else{
                        esxist=false;
                        Log.d("Inserting=","Not Exists");
                    }
                }
            });
            String url=
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyDRHaMoINsFBv0CZWBbdrdGvFhdRKWRg4E&photoreference="
                            + mResult.image;
            Glide.with(mImageView.getContext()).load(url).into(mImageView);
        }
        else {
            mErrorMessageTV.setVisibility(View.VISIBLE);
        }

    }

    private void viewLocationOnMap(){
        String Location = mResult.address;
        if(!TextUtils.isEmpty(Location)){
            Uri geoLocation = MapUtil.buildMapSearchURL(Location);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if (intent.resolveActivity(getPackageManager())!= null){
                startActivity(intent);
            }
        }
    }

    public void Add(){

        if(!esxist){
            mViewModel.insertSavedMeal(mResult);
            mToast = Toast.makeText(this, "Add Favorite Success", Toast.LENGTH_LONG);
            mToast.show();
            Log.d("Inserting=",mResult.address);
        }else{
            mToast = Toast.makeText(this, "Already Exist", Toast.LENGTH_LONG);
            mToast.show();
            Log.d("Inserting=","Exsit");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareRepo() {
        if (mResult != null) {
            String shareText = mResult.mealName + ": " + mResult.address;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
    }
}
