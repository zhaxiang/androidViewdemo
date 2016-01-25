package com.example.administrator.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Administrator on 2016/1/25.
 */
public class UtilsMethod
{
    private static String TAG = UtilsMethod.class.getSimpleName();

    public static Bitmap getBitmapByPath(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "e =" + e);
        }

        return bitmap;
    }
}
