package com.example.chooseyourmeal.data;
import java.io.Serializable;
public class MealListItemsBundle implements Serializable {
    public ResultList results[];
    public String status;
    public class ResultList{
        public String formatted_address;
        public String name;
        public String icon;
        public Photo photos[];
        public Geo geometry;
        public Hour opening_hours;
        public float rating;
    }
    public class Geo{
        public Loc location;
    }
    public class Loc{
        public Float lat;
        public Float lng;
    }
    public class Photo {
        public String photo_reference;
    }
    public class Hour {
        public String open_now;
    }
}
