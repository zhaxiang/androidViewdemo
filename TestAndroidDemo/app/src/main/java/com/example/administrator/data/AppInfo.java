package com.example.administrator.data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/8.
 */
public class AppInfo implements Parcelable
{
    private String appName = "";
    private String packageName = "";
    private String  versionName = "";
    private int versionCode = 0;
    private String appIconPath = null;
    private String appUrl = null;
    private Drawable appIcon = null;

    protected AppInfo(Parcel in)
    {
        appName = in.readString();
        packageName = in.readString();
        versionName = in.readString();
        versionCode = in.readInt();
        appIconPath = in.readString();
        appUrl = in.readString();
    }

    public AppInfo(AppInfo info)
    {
        this.appName = info.appName;
        this.packageName = info.packageName;
        this.versionName = info.versionName;
        this.versionCode = info.versionCode;
        this.appIconPath = info.appIconPath;
        this.appUrl = info.appUrl;
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>()
    {
        @Override
        public AppInfo createFromParcel(Parcel in)
        {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size)
        {
            return new AppInfo[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeString(versionName);
        dest.writeInt(versionCode);
        dest.writeString(appIconPath);
        dest.writeString(appUrl);
    }

    public AppInfo() {}

    public AppInfo(String appName, String packageName, String versionName, int versionCode, String appIcon, String appUrl)
    {
        this.appName = appName;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.appIconPath = appIcon;
        this.appUrl = appUrl;
    }
    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }

    public int getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(int versionCode)
    {
        this.versionCode = versionCode;
    }

    public String getAppIconPath()
    {
        return appIconPath;
    }

    public void setAppIconPath(String appIconPath)
    {
        this.appIconPath = appIconPath;
    }

    public String getAppUrl()
    {
        return appUrl;
    }

    public void setAppUrl(String appUrl)
    {
        this.appUrl = appUrl;
    }
}
