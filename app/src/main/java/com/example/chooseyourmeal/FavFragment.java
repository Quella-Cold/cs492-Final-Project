package com.example.chooseyourmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import com.example.chooseyourmeal.data.MealListItem;
import java.util.List;
import com.example.chooseyourmeal.data.SaveMealDao;
public class FavFragment extends Fragment {
    private RecyclerView mFavrv;
    private List<MealListItem> mMealList;
    private SaveMealDao mDao;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);

    }
}
