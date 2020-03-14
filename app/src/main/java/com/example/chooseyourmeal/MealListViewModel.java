package com.example.chooseyourmeal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chooseyourmeal.data.MealListItem;
import com.example.chooseyourmeal.data.MealRepository;

import java.util.List;

public class MealListViewModel extends AndroidViewModel {
    private LiveData<List<MealListItem>> mMealListItems;
    private LiveData<Status> mLoadingStatus;

    private MealRepository mRepository;

    public MealListViewModel(Application application) {
        super(application);
        mRepository = new ForecastRepository(application);
        mForecastItems = mRepository.getForecast();
        mLoadingStatus = mRepository.getLoadingStatus();
    }
    public void insertSavedLocation(LocationItem lc) {
        mRepository.insertSavedLocation(lc);
    }

    public void deleteSavedLocation(LocationItem lc) {
        mRepository.deleteSavedLocation(lc);
    }

    public LiveData<List<LocationItem>> getAllLocations() {
        return mRepository.getAllLocations();
    }

    public LiveData<LocationItem> getLocationByName(String fullName) {
        return mRepository.getLocationByName(fullName);
    }

    public void loadForecast(String location, String units) {
        mRepository.loadForecast(location, units);
    }

    public LiveData<List<ForecastItem>> getForecast() {
        return mForecastItems;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
