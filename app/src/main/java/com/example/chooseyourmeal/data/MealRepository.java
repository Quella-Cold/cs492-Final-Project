package com.example.chooseyourmeal.data;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import com.example.chooseyourmeal.utils.BuildPlaceApiRequestUrl;
import com.example.chooseyourmeal.data.SaveMealDao;

public class MealRepository implements LoadMealListTask.AsyncCallback {
    private static final String TAG = MealRepository.class.getSimpleName();

    private MutableLiveData<List<MealListItem>> mMealItems;
    private MutableLiveData<Status> mLoadingStatus;
    private Float mCurrentlat;
    private Float mCurrentlng;
    private String mRestaurantType;
    private String mCurrentURL;

    private SaveMealDao mDao;

    public MealRepository (Application App) {
        mMealItems = new MutableLiveData<>();
        mMealItems.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
       // AppDatabase db = AppDatabase.getDatabase(App);
       // mDAO = db.saveWeatherDao();
    }

    /*
     * This method triggers loading of new forecast data for a given location and temperature
     * units.  New data is not fetched if valid cached data exists matching the specified location
     * and units.
     */
    public void loadMeal(String restaurantType,Float lat,Float lng,Integer Re) {
        String url = BuildPlaceApiRequestUrl.BuildPlaceApiRequestUrl(restaurantType,lat,lng,Re);
        if (shouldFetchMeal(url)) {
            mRestaurantType = restaurantType;
            mCurrentlat = lat;
            mCurrentlng = lng;
            mMealItems.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            mCurrentURL=url;
            Log.d(TAG, "fetching new forecast data with this URL: " + url);
            new LoadMealListTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached forecast data");
        }
    }

    /*
     * Returns the LiveData object containing the forecast data.  An observer can be hooked to this
     * to react to changes in the forecast.
     */
    public LiveData<List<MealListItem>> getMeal() {
        return mMealItems;
    }

    /*
     * Returns the LiveData object containing the Repository's loading status.  An observer can be
     * hooked to this, e.g. to display a progress bar or error message when appropriate.
     */
    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }



    private boolean shouldFetchMeal(String url) {
        if (!TextUtils.equals(url, mCurrentURL)) {
            return true;
        } else {
            List<MealListItem> mealItems = mMealItems.getValue();
            if (mealItems == null || mealItems.size() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onMealLoadFinished(List<MealListItem> mItems) {
        mMealItems.setValue(mItems);
        if (mItems != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    public LiveData<List<MealListItem>> getAllFavMeals() {
        return mDao.getAllItems();
    }
    public LiveData<MealListItem> getFavByName(String address){
        return mDao.getLocationByName(address);
    }

}
