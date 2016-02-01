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
import com.example.administrator.fragment.CustomerFragmentManager;
import com.example.administrator.testandroiddemo.R;

public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog progDialog;

    public CustomerFragmentManager getCustomerFragmentManager()
    {
        return customerFragmentManager;
    }

    private CustomerFragmentManager customerFragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    //TODO:在onCreate中调用
    public void initFragment(Activity activity, int contentId, FragmentManager fragmentManager)
    {
        if(null == customerFragmentManager)
        {
            customerFragmentManager = CustomerFragmentManager.forContainer(activity, contentId, fragmentManager);
        }
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBack)
    {
        customerFragmentManager.replaceFragment(fragment, isAddToBack);
    }

    public void replaceFragment(int id, Fragment fragment, boolean isAddToBack)
    {
        customerFragmentManager.replaceFragment(id, fragment, isAddToBack);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.custom_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                Log.e("zhaxaing", "onOptionsItemSelected menu_settings");
                break;

            case R.id.menu_load:
                Log.e("zhaxaing", "onOptionsItemSelected menu_load");
                break;
        }


        return true;
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
    public void showProgressDialog(int id)
    {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(this.getResources().getString(id));
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    public void dissmissProgressDialog()
    {
        if (progDialog != null) {
            progDialog.dismiss();
        }
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
