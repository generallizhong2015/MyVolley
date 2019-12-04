package com.example.administrator.myvolley;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2019/12/4.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;


    @SuppressLint("NewApi")
    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;// 10m
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }
    @SuppressLint("NewApi")
    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @SuppressLint("NewApi")
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
