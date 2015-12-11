package com.example.administrator;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.testandroiddemo.service.ServiceManager;
import com.example.administrator.volley.VolleyManager;


/**
 * Created by Administrator on 2015/12/3.
 */
public class BaseApplication extends Application
{
    private final String TAG =   BaseApplication.class.getSimpleName();
    public static BaseApplication application;
    private String fragmentString = "";
    //用来记录是否成功获取了所有的应用信息，暂时不每次校验是否有删除和添加，只记录一次成功的
    public static final String APP_INFO = "AppInfo";
    public static final String APP_GET_SUCCESS = "app_get_success";
    //入口方法
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v(TAG, "onCreate");
        application = this;

        //TODO:

        //每个android的进程都会有一个自己的application，因此当启动BaseService时，
        // BaseApplication又启动 这个是BaseService进程的，会重新走一次oncreate，
        // 因此需要用此方法区分是不是当前我们需要的
        //由于此方法耗时较长，将getServiceManager()放到默认activity中启动
//        if(currentProcessNameIsDefaultPkg(this))
//        {
//            getServiceManager();
//        }

    }
          //退出时调用，不一定被调用
    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.v(TAG, "onTerminate");
    }

    //在系统内存不足，所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，系统会调用OnLowMemory。
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Log.v(TAG, "onLowMemory");
    }

    //在系统内存不足,被回调时，还有后台进程
    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.v(TAG, "onTrimMemory");
    }

    public String getFragmentString()
    {
        return fragmentString;
    }

    public void setFragmentString(String fragmentString)
    {
        this.fragmentString = fragmentString;
    }

    public ServiceManager getServiceManager()
    {
        return ServiceManager.getInstance(this.getApplicationContext());
    }

    public VolleyManager getVolleyManager()
    {
        return VolleyManager.getInstance(this.getApplicationContext());
    }

    /**
     * 当前进程名是否为默认包名
     */
    public boolean currentProcessNameIsDefaultPkg(Context context)
    {
        String currentProcName = context.getPackageName();
        int pid = android.os.Process.myPid();
        Log.v(TAG, "currentProcName =" + currentProcName + ";pid =" + currentProcName);
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses())
        {
            Log.v(TAG, "(processInfo.pid =" + processInfo.pid);
            if (processInfo.pid == pid)
            {
                if (TextUtils.equals(currentProcName, processInfo.processName))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
