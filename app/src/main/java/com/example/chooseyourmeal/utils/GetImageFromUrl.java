package com.example.chooseyourmeal.utils;

import java.io.IOException;
import java.io.InputStream;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetImageFromUrl {
    public static Bitmap GetImg(String url) {
        Bitmap bmp = null;
        try {
            URL imgUrl = new URL(url);
            URLConnection con = imgUrl.openConnection();
            InputStream in = con.getInputStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
