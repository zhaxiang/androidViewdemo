package com.example.administrator.testandroiddemo.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.data.AppInfo;
import com.example.administrator.data.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/12/3.
 */
public class BaseService extends Service
{
    private final String TAG = BaseService.class.getSimpleName();
    private IBaseServiceCallback mCallback;
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

    @Nullable
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

    private final IBaseService.Stub mBinder = new IBaseService.Stub()
    {

        @Override
        public String test(String test) throws RemoteException
        {
            Log.v(TAG, "test test= " + test);
            return "return " + test;
        }

        @Override
        public void testPerson(Person per) throws RemoteException
        {
            Log.v(TAG, "test testPerson= " + per.getName());
        }

        @Override
        public Person getPersonTest() throws RemoteException
        {
            Log.v(TAG, "getPersonTest");
            Person per = new Person("testname", "testdes", 42);
            return per;
        }

        @Override
        public void downloadImage(String url, IBaseServiceCallback cb) throws RemoteException
        {
            Log.v(TAG, "downloadImage url= " + url);
            cb.testCallback(url + ":back data");
        }

        @Override
        public Person testPersonIn(Person per) throws RemoteException
        {
            Log.v(TAG, "testPersonIn before amend per.name = " + per.getName());
            per.setName(per.getName() + "/amend");
            Log.v(TAG, "testPersonIn after amend per.name = " + per.getName());
            return per;
        }

        @Override
        public Person testPersonOut(Person per) throws RemoteException
        {
            if(per == null)
            {
                Log.v(TAG, "testPersonOut before amend per = null");
                per = new Person("new person", "des", 1);
                Log.v(TAG, "testPersonOut after amend per.name = " + per.getName());
            }
            else
            {
                Log.v(TAG, "testPersonOut before amend per.name = " + per.getName());
                per.setName(per.getName() + "/amend");
                Log.v(TAG, "testPersonOut after amend per.name = " + per.getName());
            }

            Bitmap a;
            return per;
        }

        @Override
        public Person testPersonInOut(Person per) throws RemoteException
        {
            Log.v(TAG, "testPersonInOut before amend per.name = " + per.getName());
            per.setName(per.getName() + "/amend");
            Log.v(TAG, "testPersonInOut after amend per.name = " + per.getName());
            return per;
        }

        @Override
        public List<AppInfo> getAppInfos() throws RemoteException
        {
            List<AppInfo> appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据
            List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
            for(int i=0;i<packages.size();i++)
            {
                PackageInfo packageInfo = packages.get(i);
                String path = SaveBitmapToFile(drawableToBitmap(packageInfo.applicationInfo.loadIcon(getPackageManager())), packageInfo.packageName);
                AppInfo tmpInfo =new AppInfo(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(),
                        packageInfo.packageName,packageInfo.versionName,packageInfo.versionCode, path, null);
                appList.add(tmpInfo);

            }
            return appList;
        }

        @Override
        public String SaveBitmapToFile(Bitmap bitmap, String pathName) throws RemoteException
        {
            Log.v(TAG, "pathName = " + pathName);
            String path = getFilesDir() + "/appicon/";
            String iconPath = path + pathName + ".png";

            if(null !=bitmap)
            {
                File f = new File(path);
                if(!f.exists())
                    f.mkdir();
                File iconFile = new File(iconPath);
                if(iconFile.exists())
                    iconFile.delete();
                try
                {
                    FileOutputStream out = new FileOutputStream(iconFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    Log.v(TAG, "save success");
                }
                catch (FileNotFoundException e)
                {
                    Log.v(TAG, "error = IOException");
                    iconPath = "";
                }
                catch (IOException e)
                {
                    Log.v(TAG, "error = IOException");
                    iconPath = "";
                }
            }
            return iconPath;
        }
    };

    public Bitmap drawableToBitmap(Drawable drawable)
    {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ?
                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setFilterBitmap(true);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    public Drawable bitmapToDrawable(Bitmap bitmap)
    {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        return bd;
    }
}
