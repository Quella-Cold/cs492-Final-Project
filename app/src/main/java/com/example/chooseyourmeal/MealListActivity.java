package com.example.chooseyourmeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chooseyourmeal.data.LoadMealArgs;
import com.example.chooseyourmeal.data.MealListItem;
import com.example.chooseyourmeal.data.Status;
import java.util.List;

public class MealListActivity extends AppCompatActivity implements MealListAdapter.OnMealItemClickListener {
    private static final String TAG = MealListActivity.class.getSimpleName();
    private RecyclerView mMealLitRv;
    private ProgressBar mLoadingIndicatorPB;
    private MealListAdapter mMealListAdapter;
    private  MealListViewModel mMealListViewMoedel;
    private TextView mLoadingErrorMessageTV;
   private LoadMealArgs mealArgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meallist_activity);
        mLoadingErrorMessageTV=findViewById(R.id.tv_loading_error_message);
        mLoadingIndicatorPB=findViewById(R.id.pb_loading_indicator);
        mMealLitRv=findViewById(R.id.rv_meal_items);
        mMealListAdapter=new MealListAdapter(this);
        mMealLitRv.setAdapter(mMealListAdapter);
        mMealLitRv.setLayoutManager(new LinearLayoutManager(this));
        mMealLitRv.setHasFixedSize(true);
        mMealListViewMoedel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MealListViewModel.class);
        mMealListViewMoedel.getMeal().observe(this, new Observer<List<MealListItem>>() {
            @Override
            public void onChanged(@Nullable List<MealListItem> mItems) {
                mMealListAdapter.updateMealListItems(mItems);
            }
        });
        mMealListViewMoedel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mMealLitRv.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mMealLitRv.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Information")) {
            mealArgs = (LoadMealArgs)intent.getSerializableExtra(
                    "Information"
            );
            loadMeals(mealArgs);
        }

    }

    @Override
    public void onMealItemClick(MealListItem MealListItem) {

    }

    public void loadMeals(LoadMealArgs margs){
        mMealListViewMoedel.loadMeal(margs.type,margs.lat,margs.lng,margs.radius);
    }
}
