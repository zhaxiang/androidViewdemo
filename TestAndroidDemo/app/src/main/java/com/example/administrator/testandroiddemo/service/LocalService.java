package com.example.administrator.testandroiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.data.Constants;
import com.example.administrator.volley.VolleyManager;

/**
 * Created by Administrator on 2015/12/9.
 */
public class LocalService extends Service
{
    private final String TAG = LocalService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();

     public class LocalBinder extends Binder
     {
         LocalService getLocalService()
         {
             Log.v(TAG, "getLocalService");
             return LocalService.this;
         }
     }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.v(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.v(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.v(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public VolleyManager getImageLoader()
    {
        return VolleyManager.getInstance(this);
    }
}
