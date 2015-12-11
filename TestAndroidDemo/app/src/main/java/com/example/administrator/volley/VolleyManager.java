package com.example.administrator.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.data.Constants;

/**
 * Created by Administrator on 2015/12/11.
 */
public class VolleyManager
{
    private static VolleyManager mInstance = null;

    private String TAG = VolleyManager.class.getSimpleName();

    private Context mContext = null;
    private RequestQueue mRequestQueue = null;
    private ImageLoader mImageLoader = null;

    public static VolleyManager getInstance(Context context)
    {
        if(null == mInstance)
            mInstance = new VolleyManager(context);
        return mInstance;
    }

    public VolleyManager(Context context)
    {
        mContext = context;
        getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    public RequestQueue getRequestQueue()
    {
        if(null == mRequestQueue)
            mRequestQueue = Volley.newRequestQueue(mContext);
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

    public ImageLoader getmImageLoader()
    {
        return mImageLoader;
    }

    public void getImageByUrl(String url, ImageView imageView)
    {
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, 0, 0));
    }

    public void getImageByUrl(String url, ImageView imageView, int defaultImageId)
    {
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, defaultImageId, 0));
    }

    public void getImageByUrl(String url, ImageView imageView, int defaultImageId, int errorImageId)
    {
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, defaultImageId, errorImageId));
    }
}
