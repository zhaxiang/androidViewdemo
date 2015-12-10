package com.example.administrator.testandroiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.data.Constants;

/**
 * Created by Administrator on 2015/12/9.
 */
public class LocalService extends Service
{
    private final String TAG = LocalService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private boolean mDevMode = false;

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

    public void initImageLoader()
    {
        Log.v(TAG, "initImageLoader");
//        ImageLoaderConfiguration config =null;
//        DisplayImageOptions dio = new DisplayImageOptions.Builder()
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .build();
//        if(this.isDevMode())
//        {
//            config = new ImageLoaderConfiguration.Builder(this)
//                    .defaultDisplayImageOptions(dio)
//                    .threadPriority(Thread.NORM_PRIORITY - 2)
//                    .denyCacheImageMultipleSizesInMemory()
//                    .diskCache(new UnlimitedDiscCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), Constants.APP_IMAGE)))
//                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                    .diskCacheSize(200*1024*1024)
//                    .memoryCache(new WeakMemoryCache())
//                    .tasksProcessingOrder(QueueProcessingType.LIFO)
//                            //.writeDebugLogs()
//                    .build();
//        }
//        else
//        {
//
//            config = new ImageLoaderConfiguration.Builder(this)
//                    .defaultDisplayImageOptions(dio)
//                    .threadPriority(Thread.NORM_PRIORITY - 2)
//                    .denyCacheImageMultipleSizesInMemory()
//                    .diskCache(new UnlimitedDiscCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), Constants.APP_IMAGE)))
//                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                    .diskCacheSize(200*1024*1024)
//                            //.memoryCache(new WeakMemoryCache())
//                    .tasksProcessingOrder(QueueProcessingType.LIFO)
//                    .build();
//        }
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);
    }

    public boolean isDevMode()
    {
        return mDevMode;
    }

    public void setDevMode(boolean mDevMode)
    {
        this.mDevMode = mDevMode;
    }
}
