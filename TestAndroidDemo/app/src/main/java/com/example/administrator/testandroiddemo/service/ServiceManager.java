package com.example.administrator.testandroiddemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.administrator.data.AppInfo;
import com.example.administrator.data.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class ServiceManager
{
    private static ServiceManager mInstance = null;
    private final String TAG = ServiceManager.class.getSimpleName();
    private Context mContext = null;

    private boolean isBind = false;
    private Intent mIntent = null;
    private IBaseService mService = null;
    private List<AppInfo> mAppInfos = new ArrayList<AppInfo>();

    private boolean isLocalBind = false;
    private Intent mLocalIntent = null;
    private LocalService mLocalService = null;

    public static ServiceManager getInstance(Context context)
    {
        if(null == mInstance)
        {
            mInstance = new ServiceManager(context);
        }
        return mInstance;
    }

    public ServiceManager(Context context)
    {
        mContext = context;

        mLocalIntent = new Intent(mContext, LocalService.class);
        bindLocalService();

        mIntent = new Intent(mContext, BaseService.class);
        bindService();
    }

    public boolean isLocalBind()
    {
        return isLocalBind;
    }

    public void setIsLocalBind(boolean isLocalBind)
    {
        this.isLocalBind = isLocalBind;
    }

    private void bindLocalService()
    {
        Log.v(TAG, "mLocalConnection bindLocalService");
        if(!isLocalBind)
        {
            mContext.startService(mLocalIntent);
            mContext.bindService(mLocalIntent, mLocalConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindLocalService()
    {
        Log.v(TAG, "mLocalConnection unbindLocalService");
        if(isLocalBind)
        {
            mContext.unbindService(mLocalConnection);
            mContext.stopService(mLocalIntent);
            isLocalBind = false;
        }
    }

    private ServiceConnection mLocalConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.v(TAG, "mLocalConnection onServiceConnected");
            isLocalBind = true;
            mLocalService = ((LocalService.LocalBinder)service).getLocalService();
            initImageLoader();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.v(TAG, "mLocalConnection onServiceDisconnected");
            isLocalBind = false;
            mLocalService = null;
        }
    };

    public boolean isBind()
    {
        return isBind;
    }

    public void setIsBind(boolean isBind)
    {
        this.isBind = isBind;
    }

    private void bindService()
    {
        if(!isBind)
        {
            mContext.startService(mIntent);
            mContext.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
            isBind = true;
        }
    }

    public void unBindService()
    {
        Log.v(TAG, "unBindService");
        if(isBind)
        {
            Log.v(TAG, "unBindService true");
            mContext.unbindService(mConnection);
            mContext.stopService(mIntent);
            isBind = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.v(TAG, "onServiceConnected");
            isBind = true;
            mService = IBaseService.Stub.asInterface(service);
            getAppInfos();
        }
    //网上说此方法只有意外被kill才会触发
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.v(TAG, "onServiceDisconnected");
            isBind = false;
            mService = null;
        }
    };

    public String test(String str)
    {
        Log.v(TAG, "test str = s" + str);
        String localStr = null;
        if(isBind && null != mService)
        {
            try
            {
                localStr =  mService.test(str);
            }
            catch (RemoteException e)
            {
                Log.e(TAG, "test RemoteException e" + e);
            }

        }
        return localStr;
    }

    public void testPerson(Person person)
    {
        if(isBind && null != mService)
        {
            try
            {
                mService.testPerson(person);
            }
            catch (RemoteException e)
            {
                Log.e(TAG, "testPerson RemoteException e" + e);
            }
        }
    }

    public Person getPersonTest()
    {
        Log.v(TAG, "getPersonTest");
        Person per = null;
        if(isBind && null != mService)
        {
            try
            {
                per = mService.getPersonTest();
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
        Log.v(TAG, "getPersonTest per.name = " + per.getName());
        return per;
    }

    public void dowmloadImage(String url, IBaseServiceCallback callback)
    {
        Log.v(TAG, "dowmloadImage url = " + url);
        if(isBind && null != mService)
        {
            try
            {
                mService.downloadImage(url, callback);
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Person testPersonIn(Person per)
    {
        Log.v(TAG, "testPersonIn per.name = " + per.getName());
        Person localPer = null;
        if(isBind && null != mService)
        {
            try
            {
                localPer = mService.testPersonIn(per);
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
        return localPer;
    }

    public Person testPersonOut(Person per)
    {
        if(per == null)
        {
            Log.v(TAG, "testPersonOut per = null");
        }
        else
        {
            Log.v(TAG, "testPersonOut per.name = " + per.getName());
        }

        Person localPer = null;
        if(isBind && null != mService)
        {
            try
            {
                localPer = mService.testPersonOut(per);
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
        return localPer;
    }

    public Person testPersonInOut(Person per)
    {
        Log.v(TAG, "testPersonInOut per.name = " + per.getName());
        Person localPer = null;
        if(isBind && null != mService)
        {
            try
            {
                localPer = mService.testPersonInOut(per);
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
        return localPer;
    }

    public List<AppInfo> getAppInfos()
    {
        Log.v(TAG, "getAppInfos");
        if(mAppInfos.size() <= 0)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if(isBind && null != mService)
                    {
                        try
                        {
                            mAppInfos = mService.getAppInfos();
                        } catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        Log.v(TAG, "getAppInfos size = " + mAppInfos.size());

        //TODO: 此处为了方便在第一次调用时获取数据，不发执行完毕的通知，
        // TODO：具体实现在thread中发送消息通知然后进行操作,此处数据要么为空，要么就获取到所有的
        return mAppInfos;
    }

    public void initImageLoader()
    {
        if(isLocalBind && null != mLocalService)
        {
            mLocalService.initImageLoader();
        }
    }
}
