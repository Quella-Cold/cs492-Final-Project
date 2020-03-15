package com.example.chooseyourmeal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chooseyourmeal.data.MealListItem;
import com.example.chooseyourmeal.data.MealRepository;
import com.example.chooseyourmeal.data.Status;
import java.util.List;

public class MealListViewModel extends AndroidViewModel {
    private LiveData<List<MealListItem>> mMealListItems;
    private LiveData<Status> mLoadingStatus;

    private MealRepository mRepository;

    public MealListViewModel(Application application) {
        super(application);
        mRepository = new MealRepository(application);
        mMealListItems = mRepository.getMeal();
        mLoadingStatus = mRepository.getLoadingStatus();
    }


    public void loadMeal(String restaurantType,Float lat,Float lng,Integer Re) {
        mRepository.loadMeal(restaurantType,lat,lng,Re);
    }


    public LiveData<List<MealListItem>> getMeal() {
        return mMealListItems;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
