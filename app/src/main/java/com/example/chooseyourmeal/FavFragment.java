package com.example.chooseyourmeal;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import com.example.chooseyourmeal.data.MealListItem;
import java.util.List;
import com.example.chooseyourmeal.data.SaveMealDao;
import com.example.chooseyourmeal.FavAdapter;

public class FavFragment extends Fragment implements FavAdapter.OnFavClickListener {
    private RecyclerView mFavrv;
    private FavAdapter mAdapter;
    private MealListViewModel mViewmodel;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fav, container, false);
        mFavrv=view.findViewById(R.id.rv_list_fav);
        mFavrv.setLayoutManager(new LinearLayoutManager(getContext()));
        mFavrv.setHasFixedSize(true);
        mAdapter=new FavAdapter(this);
        mFavrv.setAdapter(mAdapter);
        mViewmodel= new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(MealListViewModel.class);
        mViewmodel.getFavMeal().observe(this, new Observer<List<MealListItem>>() {
            @Override
            public void onChanged(@Nullable List<MealListItem> mItems) {

                mAdapter.updateFavItems(mItems);
            }
        });
        return view;
    }

    public void onFavClick(MealListItem item){
        Intent intent = new Intent(getContext(), MealDetailActivity.class);
        intent.putExtra("Information", item);
        startActivity(intent);
    }
}
