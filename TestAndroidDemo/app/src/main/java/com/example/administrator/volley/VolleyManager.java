package com.example.administrator.volley;

import android.app.DownloadManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.data.StringObj;
import com.example.administrator.utils.Constants;
import com.example.administrator.volley.MyVolleyListener;
import com.example.administrator.data.Weather;
import com.example.administrator.utils.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public <T> void JsonObjectRequest(String url, final MyVolleyListener<T> listener)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject)
            {
                if(listener != null)
                {
                    MyLog.e(TAG, "jsonObject =" + jsonObject.toString());

                    StringObj stringObj = new StringObj();
                    stringObj.setContent(jsonObject.toString());
                    listener.onResult("0", "message", (T)stringObj);
                }
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                MyLog.e(TAG, "error =" + volleyError.getMessage());
                if(listener != null)
                {
                    ApiResultCustom apiResultCustom = new ApiResultCustom();
                    listener.onResult("1", volleyError.getMessage(), null);
                }
            }
        });

        if(listener != null)
        {
            listener.onStart();
        }

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
                                        listener.onResult("0", "success", weather);
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
                        listener.onResult("1", error.getMessage(), error);
                }
        });
        addToRequestQueue(xmlRequest);
    }

    public <T> void stringRequest(String url, final HashMap<String, String> params, final String root, final MyVolleyListener<T> listener)
    {
        int method = Request.Method.GET;
        if(params != null)
        {
            method = Request.Method.POST;
        }
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                MyLog.e(TAG, "response =" + response);
                Class<?> c = getGenericClass(listener.getClass().getGenericSuperclass());

                ApiResultCustom apiResultCustom = parserApiResultCustom(c, response, root);

                if(listener != null)
                {
                    listener.setApiResultCustom(apiResultCustom);
                    if(apiResultCustom.getObject() != null)
                    {
                        listener.onResult(apiResultCustom.getErrorNum(), apiResultCustom.getErrorInfo(), (T)apiResultCustom.getObject());
                    }
                    else if(apiResultCustom.getList() != null)
                    {
                        listener.onResult(apiResultCustom.getErrorNum(), apiResultCustom.getErrorInfo(), (T)apiResultCustom.getList());
                    }
                    else
                    {
                        listener.onResult(apiResultCustom.getErrorNum(), apiResultCustom.getErrorInfo(), (T)apiResultCustom.getObject());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                MyLog.e(TAG, "error =" + error.getMessage());
                if(listener != null)
                {
                    ApiResultCustom apiResultCustom = new ApiResultCustom();
                    listener.onResult(apiResultCustom.getErrorNum(), apiResultCustom.getErrorInfo(), null);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                return params;
            }
        };

        if(listener != null)
        {
            listener.onStart();
        }
        addToRequestQueue(stringRequest);
    }

    public static <T> ApiResultCustom<T> parserApiResultCustom( Class<T> c, String resultString, String root) {
//        MyLog.e(TAG, "parserApiResultCustom==");
        ApiResultCustom result = new ApiResultCustom();
//        try
//        {
//            JSONObject json = new JSONObject(resultString);
//
//            result.setSource(json.toString());
//            result.setErrorNum(JsonUtils.getString(json, ApiResultCustom.CODE));
//            result.setErrorInfo(JsonUtils.getString(json, ApiResultCustom.MESSAGE));
//            MyLog.e(TAG, "result errorinfo =" + result.getErrorInfo());
//            if (c != null)
//            {
//
//                if(root==null)
//                {
//                    MyLog.e(TAG, "root==null");
//                }
//                else
//                {
//                    Object resultObject = JsonUtils.getObject(json,root);
//
//                    if (resultObject != null&&resultObject!=JSONObject.NULL)
//                    {
//                        if (resultObject instanceof JSONObject)
//                        {
//                            result.setObject(JsonUtils.parserToObjectByAnnotation(c,
//                                    JsonUtils.getJSONObject(json, root)));
//                        }
//                        else if (resultObject instanceof JSONArray)
//                        {
//                            result.setList(JsonUtils.parserToList(c,
//                                    JsonUtils.getJSONArray(json, root), true));
//                        }
//                        else
//                        {
//                            result.setObject(resultObject);
//                        }
//                    }
//                    else
//                    {
//                        MyLog.e(TAG, "c ");
//                    }
//                }
//            } else
//            {
//                if(root==null)
//                {
//                    MyLog.e(TAG, "c == null && root==null");
//                }
//                else
//                {
//                    Object resultObject = JsonUtils.getObject(json,root);
//
//                    if (resultObject != null&&resultObject!=JSONObject.NULL)
//                    {
//                        result.setObject(resultObject);
//                    }
//                    else
//                    {
//                        MyLog.e(TAG, "c== null  resultObject== null resultObject == JSONObject.NULL");
//                    }
//                }
//            }
//        }
//        catch (JSONParserException e)
//        {
//            MyLog.e(TAG, "e =" + e);
//        }
//        catch (JSONException e) {
//            MyLog.e(TAG, "e =" + e);
//        }
        return result;
    }

    /**深度递归获取泛型**/
    private Class<?> getGenericClass(Type type){
        if (type  instanceof ParameterizedType) {
            type=((ParameterizedType) type).getActualTypeArguments()[0];
            return getGenericClass(type);
        }else{
            return (Class<?>) type;
        }
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
