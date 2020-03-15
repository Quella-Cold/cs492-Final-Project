package com.example.chooseyourmeal.data;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class MealListItem implements Serializable{
    public String image;
    public String mealName;
    public String address;
    public Float lat;
    public Float lng;
}
