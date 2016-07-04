package com.example.administrator.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.BaseApplication;
import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.fragment.BaseFragment;
import com.example.administrator.fragment.CustomerFragmentManager;
import com.example.administrator.testandroiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog progDialog;

    public CustomerFragmentManager getCustomerFragmentManager()
    {
        return customerFragmentManager;
    }

    private CustomerFragmentManager customerFragmentManager = null;

    private CustomActionBar customActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        customActionBar = new CustomActionBar(this.getSupportActionBar());
        customActionBar.setCustomActionBarListener(new CustomActionBar.ActionbarClickListener()
        {
            @Override
            public void onClick(int v)
            {
                if(v == CustomActionBar.RIGHT)
                {

                }
            }
        });
    }



    //TODO:在onCreate中调用
    public void initFragment(Activity activity, int contentId, FragmentManager fragmentManager)
    {
        if(null == customerFragmentManager)
        {
            customerFragmentManager = CustomerFragmentManager.forContainer(activity, contentId, fragmentManager);
        }
    }

    public void replaceFragment(BaseFragment fragment, boolean isAddToBack)
    {
        customerFragmentManager.replaceFragment(fragment, isAddToBack);
    }

    public void replaceFragment(int id, BaseFragment fragment, boolean isAddToBack)
    {
        customerFragmentManager.replaceFragment(id, fragment, isAddToBack);
    }

    public CustomActionBar getCustomActionBar()
    {
        return customActionBar;
    }

    @Override
    public void onBackPressed()
    {
        Log.v("BaseActivity", "onBackPressed");
        if(null != customerFragmentManager)
        {
            if(customerFragmentManager.popFragment())
            {
            }
            else
            {
                super.onBackPressed();
            }
        }
        else
        {
            super.onBackPressed();
        }

    }

    /**
     * 显示进度框
     */
    public ProgressDialog showProgressDialog(int id)
    {
        ProgressDialog  progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(this.getResources().getString(id));
        progDialog.show();

        return  progDialog;
    }

    public ProgressDialog showProgressDialog(String id)
    {
        ProgressDialog  progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(id);
        progDialog.show();

        return  progDialog;
    }

    public void showToast(int id)
    {
        BaseApplication.application.showToast(id);
    }

    public void showToast(String content)
    {
        BaseApplication.application.showToast(content);
    }

    public void showToast(int id, String content)
    {
        BaseApplication.application.showToast(id, content);
    }
}
