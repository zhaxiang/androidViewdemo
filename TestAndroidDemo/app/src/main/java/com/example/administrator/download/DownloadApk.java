package com.example.administrator.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.data.Constants;

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
    private DownLoadCompleteReceiver receiver;
    private long currentTaskId = -1;
    private Context mContext;
    private String filePath;
    private long totalSize = -1;

    public static DownloadApk getInstance(Context context)
    {
        if(null == mDownloadApk)
            mDownloadApk = new DownloadApk(context);
        return mDownloadApk;
    }

    DownloadApk(Context context)
    {
        mContext = context;
        downManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        if(null != receiver)
            mContext.unregisterReceiver(receiver);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        receiver = new DownLoadCompleteReceiver();
        mContext.registerReceiver(receiver, filter);
    }

    public void removeDownloadApk()
    {
        if(-1 != currentTaskId)
        {
            downManager.remove(currentTaskId);//此方法会连同apk一起删除
            init();
        }
    }

    public void downloadApk(String url, String notificationTitle, String notificationDes, DownloadListener listener)
    {
        Log.v(TAG, "currentTaskId =" + currentTaskId);
        if(-1 == currentTaskId)
        {
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
            //设置文件存放目录
            request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, Constants.DOWNLOAD_APK_NAME);

            currentTaskId = downManager.enqueue(request);
            if(null != mListener)
                mListener.start();
            Log.v(TAG, "currentTaskId != -1 : =" + currentTaskId);

            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    queryTaskById(currentTaskId);
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    private void queryTaskById(long id)
    {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor= downManager.query(query);
        long size = -1;
        if(cursor.moveToNext())
        {
            size = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            if(-1 == totalSize)
                totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            if(null == filePath)
                filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        }
        cursor.close();
//        progressBar.setMax(Integer.valueOf(sizeTotal));
//        progressBar.setProgress(Integer.valueOf(size));
        if(null != mListener)
        {
            mListener.progress(size, totalSize);
        }

    }

    private class DownLoadCompleteReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if(id == currentTaskId)
                {
                    if(null != mListener)
                    {
                        mListener.progress(totalSize, totalSize);
                        mListener.success();
                        installApk();
                    }
                }

//                Toast.makeText(MainActivity.this, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
            }
            else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED))
            {
//                Toast.makeText(MainActivity.this, "别瞎点！！！", Toast.LENGTH_SHORT).show();
                if(null != mListener)
                    mListener.cancel();
                removeDownloadApk();

            }
        }

        private void installApk()
        {
            if(null != filePath)
            {

                File tmp = new File(filePath);
                if (tmp.getAbsolutePath().startsWith(Environment.getDataDirectory().getAbsolutePath()))
                {
                    while (null != tmp && tmp.exists())
                    {
                        try
                        {
                            Runtime.getRuntime().exec(new String[]{"chmod", "777",tmp.getAbsolutePath()});
                            tmp = tmp.getParentFile();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    Log.v(TAG, "installApk filePath = " + filePath);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(tmp),
                            "application/vnd.android.package-archive");
                    mContext.startActivity(intent);
                }


            }
        }
    }

    private void init()
    {
        mListener = null;
        totalSize = -1;
        filePath = null;
        currentTaskId = -1;
    }
}
