package com.example.administrator.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/12/29.
 */
public class DownloadApk
{
    private final String TAG = DownloadApk.class.getSimpleName();
    private static DownloadApk mDownloadApk = null;
    private DownloadListener mListener;
    private DownloadManager downManager;
    private Context mContext;
    private long totalSize = -1;
    private ScheduledExecutorService scheduledExecutorService;

    public static DownloadApk getInstance(Context context)
    {
        if(null == mDownloadApk)
            mDownloadApk = new DownloadApk(context);
        return mDownloadApk;
    }

    public static DownloadApk getInstance()
    {
        return mDownloadApk;
    }

    DownloadApk(Context context)
    {
        mContext = context;
        downManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void removeDownloadApk()
    {
        long currnetId = mContext.getSharedPreferences(Constants.DOWNLOAD_INFO, Activity.MODE_PRIVATE).getLong(Constants.DOWNLOAD_NAME_ID, -1);
        if(-1 != currnetId)
        {
            downManager.remove(currnetId);//此方法会连同apk一起删除
            init();
        }
    }

    public void downloadApk(String url, String notificationTitle, String notificationDes, DownloadListener listener)
    {
        removeDownloadApk();

        File folder = new File(Constants.DOWNLOAD_FILE_NAME);
        if (!folder.exists() || !folder.isDirectory())
        {
            folder.mkdirs();
        }

        mListener = listener;
        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(url));
        //设置在什么网络情况下进行下载
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        if(null != notificationDes && null != notificationTitle)
        {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle(notificationTitle);//上标题  下载
            request.setDescription(notificationDes);//下标题 今日头条正在下载
        }
        //设置漫游状态无法下载
        request.setAllowedOverRoaming(false);
        //不显示下载界面
        request.setVisibleInDownloadsUi(false);
        //设置文件存放目录
//        request.setDestinationInExternalPublicDir(Constants.DOWNLOAD_FILE_NAME, Constants.DOWNLOAD_APK_NAME);
        request.setDestinationInExternalFilesDir(mContext, Constants.DOWNLOAD_FILE_NAME, Constants.DOWNLOAD_APK_NAME);
        final long currentTaskId = downManager.enqueue(request);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.DOWNLOAD_INFO, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.DOWNLOAD_NAME_ID, currentTaskId);
        editor.commit();
        if(null != mListener)
            mListener.start();
        Log.v(TAG, "currentTaskId != -1 : =" + currentTaskId);
        if(scheduledExecutorService != null)
        {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                queryTaskById(currentTaskId);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void queryTaskById(long id)
    {
        Log.e(TAG, "id" + id);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor= downManager.query(query);
        long size = -1;
        if(cursor.moveToNext())
        {
            size = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            if(-1 == totalSize)
                totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//            if(null == filePath)
//                filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        }
        cursor.close();
//        progressBar.setMax(Integer.valueOf(sizeTotal));
//        progressBar.setProgress(Integer.valueOf(size));
        if(null != mListener)
        {
            mListener.progress(size, totalSize);
        }

    }

    private void installApk(String filePath)
    {
        if(null != filePath)
        {

            File file = new File(filePath);
            if (file != null && file.length() > 0 && file.exists() && file.isFile())
            {
//                    try
//                    {
//                        Runtime.getRuntime().exec(new String[]{"chmod", "777",file.getAbsolutePath()});
//                        file = file.getParentFile();
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }

                Log.v(TAG, "installApk filePath = " + filePath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setDataAndType(Uri.fromFile(file),
//                            "application/vnd.android.package-archive");
                intent.setDataAndType(Uri.parse("file://" + filePath),
                        "application/vnd.android.package-archive");
                mContext.startActivity(intent);
            }


        }
    }
    public void success()
    {
        if(scheduledExecutorService != null)
        {
            Log.e(TAG, "DownLoadCompleteReceiver scheduledExecutorService != null");
            scheduledExecutorService.shutdown();
        }
        if(null != mListener)
        {
            Log.e(TAG, "DownLoadCompleteReceiver mListener");
            mListener.progress(totalSize, totalSize);
            mListener.success();
            //            String apkFilePath = new StringBuilder(Environment
//                    .getExternalStorageDirectory().getAbsolutePath())
//                    .append(File.separator)
//                    .append(ConstantsNew.DOWNLOAD_FILE_NAME)
//                    .append(File.separator).append(ConstantsNew.DOWNLOAD_APK_NAME)
//                    .toString();

            String apkFilePath = new StringBuilder(mContext
                    .getExternalFilesDir(null).getAbsolutePath())
                    .append(File.separator)
                    .append(Constants.DOWNLOAD_FILE_NAME)
                    .append(File.separator).append(Constants.DOWNLOAD_APK_NAME)
                    .toString();
            Log.e(TAG, "DownLoadCompleteReceiver apkFilePath =" + apkFilePath);
            installApk(apkFilePath);
        }
    }

    private void init()
    {
        mListener = null;
        totalSize = -1;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.DOWNLOAD_INFO, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.DOWNLOAD_NAME_ID, -1);
        editor.commit();
    }
}
