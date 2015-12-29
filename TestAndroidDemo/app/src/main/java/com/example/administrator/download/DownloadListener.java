package com.example.administrator.download;

/**
 * Created by Administrator on 2015/12/29.
 */
public interface DownloadListener
{
    public void start();
    public void progress(long currentSize, long totalSize);
    public void success();
    public void cancel();
    public void fail();
}
