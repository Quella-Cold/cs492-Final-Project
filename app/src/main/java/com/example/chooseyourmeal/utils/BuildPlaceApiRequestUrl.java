package com.example.chooseyourmeal.utils;

public class BuildPlaceApiRequestUrl {
    public static String requestPrefix="https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDRHaMoINsFBv0CZWBbdrdGvFhdRKWRg4E&query=";

    public static String BuildPlaceApiRequestUrl (String keyword,Float lat,Float lng,Integer Radius){
          String Rurl;
          Rurl=requestPrefix+keyword+"&location="+lat.toString()+","+lng.toString()+"&radius="+Radius.toString();
          return Rurl;
    }
}
