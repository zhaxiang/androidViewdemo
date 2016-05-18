package com.example.administrator.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.data.Constants;

import java.io.File;

/**
 * Created by Administrator on 2016/3/2.
 */
public class DownLoadCompleteReceiver extends BroadcastReceiver
{
    private static final String TAG = DownLoadCompleteReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "DownLoadCompleteReceiver");
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e(TAG, "DownLoadCompleteReceiver id = " + id);
            long currnetId = context.getSharedPreferences(Constants.DOWNLOAD_INFO, Activity.MODE_PRIVATE).getLong(Constants.DOWNLOAD_NAME_ID, -1);
            Log.e(TAG, "DownLoadCompleteReceiver currnetId = " + currnetId);
            if(id == currnetId)
            {
                {
                    DownloadApk.getInstance().success();
                }

            }

            Toast.makeText(context, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED))
        {
            Toast.makeText(context, "别瞎点！！！", Toast.LENGTH_SHORT).show();
        }
    }
}
