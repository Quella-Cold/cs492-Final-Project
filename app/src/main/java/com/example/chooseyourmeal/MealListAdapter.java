package com.example.chooseyourmeal;

import android.content.Intent;
import androidx.core.app.ShareCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chooseyourmeal.data.MealListItem;

import java.text.DateFormat;
import java.util.List;
public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealListViewHolder> {
    private List<MealListItem> mMealListItem;
    private OnMealItemClickListener mMealItemClickListener;
    public interface OnMealItemClickListener {
        void onMealItemClick(MealListItem MealListItem);
    }
    public void updateMealListItems(List<MealListItem> MealListItems) {
        mMealListItem = MealListItems;
        //this.UnitAbbr=UnitAbbr;
        notifyDataSetChanged();
    }
    public MealListAdapter(OnMealItemClickListener ItemListener){
        mMealItemClickListener=ItemListener;
    }
    public MealListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.cardview_adapter, parent, false);
        return null;
    }
    @Override
    public int getItemCount() {
        if (mMealListItem != null) {
            return mMealListItem.size();
        } else {
            return 0;
        }
    }
    @Override
    public void onBindViewHolder(MealListViewHolder holder, int position) {
        holder.bind(mMealListItem.get(position));
    }

    class MealListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mForecastDateTV;
        private TextView mForecastTempDescriptionTV;
        private ImageView mWeatherIconIV;

        public MealListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(MealListItem mealListItem) {


        }

        @Override
        public void onClick(View v) {

        }
    }
}
