package com.example.chooseyourmeal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chooseyourmeal.data.ListTypeItem;

import java.util.ArrayList;

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ListTypeViewHolder> {

    private ArrayList<ListTypeItem> mListTypes;
    private OnListTypeItemClickListener onListTypeItemClickListener;

    public interface OnListTypeItemClickListener {
        void onListTypeItemClick(ListTypeItem listType);
    }

    public ListTypeAdapter(OnListTypeItemClickListener clickListener){
        onListTypeItemClickListener = clickListener;
    }

    public void setListTypes(ArrayList<ListTypeItem> ListTypes) {
        mListTypes = ListTypes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new ListTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTypeViewHolder holder, int position) {
        holder.bind(mListTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mListTypes.size();
    }

    class ListTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mListTypeIV;
        private TextView mListTypeTV;

        public ListTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            mListTypeIV = itemView.findViewById(R.id.iv_list_pic);
            mListTypeTV = itemView.findViewById(R.id.tv_list);
            itemView.setOnClickListener(this);
        }

        public void bind(ListTypeItem listType) {
            mListTypeTV.setText(listType.Type);
            Glide.with(mListTypeIV.getContext())
                    .load(listType.TypeImageURL)
                    .into(mListTypeIV);
        }

        @Override
        public void onClick(View v) {
            String type = mListTypes.get(getAdapterPosition()).Type;
            Log.d("Type", type);
        }
    }

}
