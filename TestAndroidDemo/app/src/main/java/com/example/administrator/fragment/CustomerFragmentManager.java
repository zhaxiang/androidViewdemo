package com.example.administrator.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by Administrator on 2015/12/7.
 */
public class CustomerFragmentManager
{
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private int fragmentContent;
    private static CustomerFragmentManager customerFragmentManager = null;

    CustomerFragmentManager forContainer()
    {
        if(null == customerFragmentManager)
        {
            customerFragmentManager = new CustomerFragmentManager();
        }
        return customerFragmentManager;
    };

    CustomerFragmentManager()
    {
        if(fragmentManager == null);

    };

    void initFragmentContent(int id)
    {
        fragmentContent = id;
    };

    void setFragmentContent(int id)
    {
        fragmentContent = id;
    };

    int getFragmentContent()
    {
        return fragmentContent;
    }

    void replace(Fragment fragment)
    {
        fragmentTransaction.replace(fragmentContent, fragment);
    };
}
