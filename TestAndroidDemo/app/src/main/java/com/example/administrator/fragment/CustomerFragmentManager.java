package com.example.administrator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import com.example.administrator.testandroiddemo.R;

/**
 * Created by Administrator on 2015/12/7.
 */
public class CustomerFragmentManager
{
    private FragmentManager fragmentManager = null;
    private Activity mActivity = null;
    private int fragmentContent;

    public static CustomerFragmentManager forContainer(Activity activity, int contentId, FragmentManager fragmentManager)
    {
         return new CustomerFragmentManager(activity, contentId, fragmentManager);
    }

    CustomerFragmentManager(Activity activity, int contentId, FragmentManager fragmentManager)
    {
        this.mActivity = activity;
        fragmentContent = contentId;
        if(fragmentManager == null)
        {
            this.fragmentManager = activity.getFragmentManager();
        }
        else
        {
            this.fragmentManager = fragmentManager;
        }


    }

    void initFragmentContent(int id)
    {
        fragmentContent = id;
    }

    void setFragmentContent(int id)
    {
        fragmentContent = id;
    }

    int getFragmentContent()
    {
        return fragmentContent;
    }

    Activity getFramentContentActivity(){return mActivity;}

    public void replaceFragment(Fragment fragment, boolean isAddToBack)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.e("zhaxiang", "fragment =" + fragment.getClass().getSimpleName());
        fragmentTransaction.replace(fragmentContent, fragment, fragment.getClass().getSimpleName());
        if(isAddToBack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    };

    //此方法是为给在xml中定义好FrameLayout添加对应布局使用
    public void addFragment(int id, Fragment fragment, boolean isAddToBack)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.e("zhaxiang", "fragment =" + fragment.getClass().getSimpleName());
        fragmentTransaction.replace(id, fragment, fragment.getClass().getSimpleName());
        if(isAddToBack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    };

    public boolean popFragment()
    {
        return fragmentManager.popBackStackImmediate();
    }
}
