package com.example.chooseyourmeal.data;

import java.io.Serializable;

public class LoadMealArgs implements Serializable {
    public String type;
    public Float lat;
    public Float lng;
    public Integer radius;
}