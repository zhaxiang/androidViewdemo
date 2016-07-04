package com.example.administrator.volley;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.utils.Constants;

import android.util.LruCache;

/**
 * Created by Administrator on 2015/12/11.
 */
public class BitmapCache implements ImageLoader.ImageCache
{
    private LruCache<String, Bitmap> mCache;

    public BitmapCache()
    {
        int maxSize = Constants.BITMAP_CACHE_SIZE * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s)
    {
        return mCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap)
    {
        mCache.put(s, bitmap);
    }
}
