package com.example.administrator.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.example.administrator.BaseApplication;
import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.data.Constants;
import com.example.administrator.fragment.ActionbarFragment;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.fragment.EventBusFragment;
import com.example.administrator.fragment.GaodeMapFragment;
import com.example.administrator.fragment.GridViewFragment;
import com.example.administrator.fragment.PercentlayoutFragment;
import com.example.administrator.fragment.SlidingPaneLayout.SlidingPaneLayoutFragment;
import com.example.administrator.fragment.SortListViewFragment;
import com.example.administrator.fragment.UpdateApkFragment;
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

        //TODO:  getFragmentManager()此方法是minsdk 不小于14时使用，且已经不支持findFragmentById
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
        else if(currentFragment == Constants.SlidingPaneLayoutFragment)
        {
            titleId = R.string.sliding_pane_layout_demo;
            showSlidingPaneLayoutFragment();
        }
        else if(currentFragment == Constants.UpdateApkFragment)
        {
            titleId = R.string.update_apk_demo;
            showUpdateApkFragment();
        }
        else if(currentFragment == Constants.GridViewFragment)
        {
            titleId = R.string.gridview_test;
            showGridViewFragment();
        }
        else if(currentFragment == Constants.PercentlayoutFragment)
        {
            titleId = R.string.percent_layout;
            showPercentLayoutFragment();
        }

        else if(currentFragment == Constants.ActionbarFragment)
        {
            titleId = R.string.actionbar_custom_test;
            showActionbarFragment();
        }
        else if(currentFragment == Constants.CalendarFragment)
        {
            titleId = R.string.date_picker;
            showCalendarFragment();
        }
        else if(currentFragment == Constants.SortListViewFragment)
        {
            titleId = R.string.sort_list_view;
            showSortListViewFragment();
        }

        getCustomActionBar().setTitle(titleId);
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

    private void showSlidingPaneLayoutFragment()
    {
        Log.v(TAG, "showSlidingPaneLayoutFragment");
        replaceFragment(new SlidingPaneLayoutFragment(), false);
    }

    private void showUpdateApkFragment()
    {
        Log.v(TAG, "showUpdateApkFragment");
        replaceFragment(new UpdateApkFragment(), false);
    }

    private void showGridViewFragment()
    {
        Log.v(TAG, "showGridViewFragment");
        replaceFragment(new GridViewFragment(), false);
    }

    private void showPercentLayoutFragment()
    {
        Log.v(TAG, "showPercentLayoutFragment");
        replaceFragment(new PercentlayoutFragment(), false);
    }

    private void showActionbarFragment()
    {
        Log.v(TAG, "showActionbarFragment");
        replaceFragment(new ActionbarFragment(), false);
    }

    private void showCalendarFragment()
    {
        Log.v(TAG, "showCalendarFragment");
        replaceFragment(new CalendarFragment(), false);
    }
    private void showSortListViewFragment()
    {
        Log.v(TAG, "showSortListViewFragment");
        replaceFragment(new SortListViewFragment(), false);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
