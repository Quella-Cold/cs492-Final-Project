package com.example.chooseyourmeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chooseyourmeal.data.CurrentLocation;
import com.example.chooseyourmeal.data.ListTypeItem;
import com.example.chooseyourmeal.data.LoadMealArgs;
import com.example.chooseyourmeal.MealListActivity;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements ListTypeAdapter.OnListTypeItemClickListener{

    private ArrayList<ListTypeItem> mListTypes;
    private RecyclerView mListTypesRV;
    private ListTypeAdapter mListTypeAdapter;

    private CurrentLocation mCurrentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mListTypes = new ArrayList<>();
        mListTypes.add(new ListTypeItem("American Food", "https://images.pexels.com/photos/1059847/pexels-photo-1059847.jpeg?cs=srgb&dl=beef-burger-beef-burger-and-chips-beefburger-burger-and-1059847.jpg&fm=jpg"));
        mListTypes.add(new ListTypeItem("Chinese Food", "https://images.pexels.com/photos/1059562/pexels-photo-1059562.jpeg?cs=srgb&dl=pexels-1059562.jpg&fm=jpg"));
        mListTypes.add(new ListTypeItem("Italian Food", "https://images.pexels.com/photos/1738458/pexels-photo-1738458.jpeg?cs=srgb&dl=festa-italiana-1738458.jpg&fm=jpg"));
        mListTypes.add(new ListTypeItem("Japanese Food", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT97e_K-3n1a5fUdqTGwZ0dFP3RcwjOvGWqN_NiMnMg05wuBnAd"));
        mListTypes.add(new ListTypeItem("Mexican Food", "https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?cs=srgb&dl=burrito-chicken-delicious-dinner-461198.jpg&fm=jpg"));
        mListTypes.add(new ListTypeItem("Thai Food", "https://images.pexels.com/photos/1413491/pexels-photo-1413491.jpeg?cs=srgb&dl=asian-food-dinner-dish-food-photography-1413491.jpg&fm=jpg"));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mListTypesRV = view.findViewById(R.id.rv_list);
        mListTypesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mListTypesRV.setHasFixedSize(true);

        mListTypeAdapter = new ListTypeAdapter(this);
        mListTypesRV.setAdapter(mListTypeAdapter);

        mListTypeAdapter.setListTypes(mListTypes);
        return view;
    }

    @Override
    public void onListTypeItemClick(ListTypeItem listType) {
        String type = listType.Type;
        updateLocation();
        LoadMealArgs args=new LoadMealArgs();
        args.lat=Float.parseFloat(mCurrentLocation.lat);
        args.lng=Float.parseFloat(mCurrentLocation.lon);
        args.radius=500;
        args.type=type;
        Intent intent = new Intent(getContext(), MealListActivity.class);
        intent.putExtra("Information", args);
        startActivity(intent);
        Log.d("type", type);
        Log.d("type", mCurrentLocation.lat + " " + mCurrentLocation.lon);
    }

    public void updateLocation() {
        mCurrentLocation = ((MainActivity) getActivity()).getCurrentLocation();
    }
}
