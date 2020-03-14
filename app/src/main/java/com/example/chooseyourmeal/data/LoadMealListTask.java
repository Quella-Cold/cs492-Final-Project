package com.example.chooseyourmeal.data;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.chooseyourmeal.utils.NetworkUtils;
import com.google.gson.Gson;
import com.example.chooseyourmeal.utils.BuildPlaceApiRequestUrl;
import com.example.chooseyourmeal.data.MealListItemsBundle;
class LoadMealListTask extends AsyncTask<Void, Void, String> {
    public String requestPrefix="https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDRHaMoINsFBv0CZWBbdrdGvFhdRKWRg4E&query=";
    public interface AsyncCallback {
        void onMealLoadFinished(List<MealListItem> MealItems);
    }

    private String mURL;
    private AsyncCallback mCallback;

    LoadMealListTask(String URL, AsyncCallback callback) {
        mURL = URL;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String mealListJSON = null;
        try {
            mealListJSON = NetworkUtils.doHttpGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mealListJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<MealListItem> MealItems = null;
        if (s != null) {
            Gson gson= new Gson();
            MealListItemsBundle res=gson.fromJson(s,MealListItemsBundle.class);
            if(res!=null && res.results!=null){
                for(MealListItemsBundle.ResultList ritem:res.results){
                    MealListItem mealItem = new MealListItem();
                    mealItem.mealName=ritem.name;
                    mealItem.image=ritem.photos[0].photo_reference;
                    mealItem.lat=ritem.geometry.location.lat;
                    mealItem.lng=ritem.geometry.location.lng;
                    mealItem.address=ritem.formatted_address;
                    MealItems.add(mealItem);
                }
            }
        }
        mCallback.onMealLoadFinished(MealItems);
    }
}
