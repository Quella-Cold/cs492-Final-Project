package com.example.chooseyourmeal.utils;

import android.net.Uri;

public class MapUtil {
    private final static String MAP_SEARCH_BASE_URL = "geo:0,0";
    private final static String MAP_SEARCH_PARAM = "q";


    public static Uri buildMapSearchURL(String location) {
        return Uri.parse(MAP_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(MAP_SEARCH_PARAM, location)
                .build();
    }
}
