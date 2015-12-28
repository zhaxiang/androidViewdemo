package com.example.administrator.data;

/**
 * Created by Administrator on 2015/12/23.
 */
public interface MyVolleyListener<T>
{
    public void myVolleyListener(int result, T a);//result = 0表示成功 其他表示失败
}
