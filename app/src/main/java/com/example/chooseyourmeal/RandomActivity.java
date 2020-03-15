package com.example.chooseyourmeal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class RandomActivity extends AppCompatActivity {

    private LoadMealArgs mealArgs;
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

    private MealListItemsBundle.ResultList mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_activity);

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


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Information")) {
            mealArgs = (LoadMealArgs) intent.getSerializableExtra(
                    "Information"
            );
        }
        new getRandom().execute(mealArgs);

        mNavBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLocationOnMap();
            }
        });

        mFavBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFav();
            }
        });
    }

    public class getRandom extends AsyncTask<LoadMealArgs, Void, MealListItemsBundle.ResultList>{

        @Override
        protected void onPreExecute() {
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
            mErrorMessageTV.setVisibility(View.INVISIBLE);
            mll.setVisibility(View.INVISIBLE);
            super.onPreExecute();
        }

        @Override
        protected MealListItemsBundle.ResultList doInBackground(LoadMealArgs... loadMealArgs) {
            String type = loadMealArgs[0].type;
            Float lat = loadMealArgs[0].lat;
            Float lng = loadMealArgs[0].lng;
            int radius = loadMealArgs[0].radius;
            String url = BuildPlaceApiRequestUrl.BuildPlaceApiRequestUrl(type, lat, lng, radius);
            String searchResults = null;
            try{
                searchResults = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson= new Gson();
            MealListItemsBundle res = gson.fromJson(searchResults,MealListItemsBundle.class);
            Random rand = new Random();
            MealListItemsBundle.ResultList result = null;
            if (res.results.length != 0) {
                result = res.results[rand.nextInt(res.results.length)];
                mResult = result;
            }
            return result;
        }

        @Override
        protected void onPostExecute(MealListItemsBundle.ResultList resultList) {
            super.onPostExecute(resultList);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (resultList != null){
                mll.setVisibility(View.VISIBLE);
                mNameTV.setText(resultList.name);
                if(TextUtils.equals(resultList.opening_hours.open_now, "true")) {
                    mOpenTV.setText("Yes");
                } else {
                    mOpenTV.setText("No");
                }
                String rate = String.valueOf(resultList.rating);
                mRatingTV.setText(rate);
                mAddressTV.setText(resultList.formatted_address);
                String url=
                        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyDRHaMoINsFBv0CZWBbdrdGvFhdRKWRg4E&photoreference="
                                + resultList.photos[0].photo_reference;
                Glide.with(mImageView.getContext()).load(url).into(mImageView);
            }
            else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void viewLocationOnMap(){
        String Location = mResult.formatted_address;
        if(!TextUtils.isEmpty(Location)){
            Uri geoLocation = MapUtil.buildMapSearchURL(Location);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if (intent.resolveActivity(getPackageManager())!= null){
                startActivity(intent);
            }
        }
    }

    private void addToFav(){
        MealListItem item = new MealListItem();
        item.image = mResult.photos[0].photo_reference;
        item.mealName = mResult.name;
        item.rating = mResult.rating;
        item.address = mResult.formatted_address;
        item.open = mResult.opening_hours.open_now;
        item.lat = mResult.geometry.location.lat;
        item.lng = mResult.geometry.location.lng;

        //add item to database
    }
}