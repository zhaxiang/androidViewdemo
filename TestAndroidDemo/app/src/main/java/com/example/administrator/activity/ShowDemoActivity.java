package com.example.administrator.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.BaseApplication;
import com.example.administrator.data.Constants;
import com.example.administrator.fragment.CustomerFragmentManager;
import com.example.administrator.fragment.EventBusFragment;
import com.example.administrator.fragment.GaodeMapFragment;
import com.example.administrator.fragment.VolleyFragment;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.fragment.ListDemoFragment;

public class ShowDemoActivity extends BaseActivity
{
    private final String TAG = ShowDemoActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_demo);

        initFragment(this, R.id.main_frament, getFragmentManager());

        initView();
    }

    private void initView()
    {
        String currentFragment = BaseApplication.application.getFragmentString();
        int titleId = 0;
        if(currentFragment == Constants.ListFragment)
        {
            titleId = R.string.list_refresh_show;
            showListFragment();
        }
        else if(currentFragment == Constants.VolleyFragment)
        {
            titleId = R.string.volley_interface_show;
            showVolleyFragment();
        }
        else if(currentFragment == Constants.GaodeMapFragment)
        {
            titleId = R.string.map_gaode;
            showGaodeMapFragment();

        }
        else if(currentFragment == Constants.EventBusFragment)
        {
            titleId = R.string.event_bus_demo;
            showEventBusFragment();
        }
        this.getSupportActionBar().setTitle(titleId);
    }

    private void showListFragment()
    {
        Log.v(TAG, "showListFragment");
        replaceFragment(new ListDemoFragment(), false);
    }

    private void showVolleyFragment()
    {
        Log.v(TAG, "showVolleyFragment");
        replaceFragment(new VolleyFragment(), false);
    }
    private void showGaodeMapFragment()
    {
        Log.v(TAG, "showGaodeMapFragment");
        replaceFragment(new GaodeMapFragment(), false);
    }
    private void showEventBusFragment()
    {
        Log.v(TAG, "showEventBusFragment");
        replaceFragment(new EventBusFragment(), false);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
