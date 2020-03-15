package com.example.chooseyourmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chooseyourmeal.data.MealListItem;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private List<MealListItem> mMealListItem;
    private OnFavClickListener mFavClickListener;
    public interface OnFavClickListener {
        void onFavClick(MealListItem MealListItem);
    }
    public void updateFavItems(List<MealListItem> MealListItems) {
        mMealListItem = MealListItems;
        //this.UnitAbbr=UnitAbbr;
        notifyDataSetChanged();
    }
    public FavAdapter(OnFavClickListener ItemListener){
        mFavClickListener=ItemListener;
    }
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.cardview_adapter, parent, false);
        return new FavViewHolder(itemView);
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
    public void onBindViewHolder(FavViewHolder holder, int position) {
        holder.bind(mMealListItem.get(position));
    }

    class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextview;
        private TextView mTextviewaddr;
        private ImageView mImageView;

        public FavViewHolder(View itemView) {
            super(itemView);
            mTextview=itemView.findViewById(R.id.cv_textview_name);
            mTextviewaddr=itemView.findViewById(R.id.cv_textview_address);
            mImageView=itemView.findViewById(R.id.cv_imageview);
            itemView.setOnClickListener(this);
        }

        public void bind(MealListItem mealListItem) {
            mTextview.setText(mealListItem.mealName);
            mTextviewaddr.setText(mealListItem.address);

        }

        @Override
        public void onClick(View v) {
            MealListItem mitem=mMealListItem.get(getAdapterPosition());
            mFavClickListener.onFavClick(mitem);
        }
    }
}