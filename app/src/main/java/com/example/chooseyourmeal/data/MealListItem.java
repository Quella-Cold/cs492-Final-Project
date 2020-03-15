package com.example.chooseyourmeal.data;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "MealItems")
public class MealListItem implements Serializable{
    @PrimaryKey
    @NonNull
    public String address;
    public String image;
    public String mealName;
    public Float lat;
    public Float lng;
}
