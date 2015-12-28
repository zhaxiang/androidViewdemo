package com.example.administrator.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.data.Constants;
import com.example.administrator.data.MyVolleyListener;
import com.example.administrator.data.Weather;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

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

    public void JsonObjectRequest(String url, final MyVolleyListener listener)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject)
            {
                listener.myVolleyListener(0, jsonObject.toString());
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                listener.myVolleyListener(1, "volleyError = " + volleyError.getMessage());
            }
        });

        addToRequestQueue(jsonRequest);
    }

    public void XMLRequest(String url, final MyVolleyListener listener)
    {
        XMLRequest xmlRequest = new XMLRequest(url, new Response.Listener<XmlPullParser>()
        {
            @Override
            public void onResponse(XmlPullParser response)
            {
                try
                {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT)
                    {
                        switch (eventType)
                        {
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName))
                                {
                                    String pName = response.getAttributeValue(0);
                                    Log.v("TAG", "pName is " + pName);
                                    Weather weather = new Weather(response.getAttributeValue(0), response.getAttributeValue(1),
                                            response.getAttributeValue(2), Integer.parseInt(response.getAttributeValue(3)),
                                            Integer.parseInt(response.getAttributeValue(4)), response.getAttributeValue(5),
                                            Integer.parseInt(response.getAttributeValue(6)), Integer.parseInt(response.getAttributeValue(7)),
                                            response.getAttributeValue(8));
                                    if(null != listener)
                                        listener.myVolleyListener(0, weather);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                }
                catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.e("TAG", error.getMessage(), error);
                    if(null != listener)
                        listener.myVolleyListener(1, null);
                }
        });
        addToRequestQueue(xmlRequest);
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
