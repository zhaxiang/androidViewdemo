package com.example.administrator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.testandroiddemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2015/12/7.
 */
public class CustomerFragmentManager
{
    private final String TAG = CustomerFragmentManager.class.getSimpleName();

    private FragmentManager fragmentManager = null;
    private Activity mActivity = null;
    private int fragmentContent;

    private Stack<BaseFragment> baseFragmentList = new Stack<>();

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

    public void replaceFragment(BaseFragment fragment, boolean isAddToBack)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.e("zhaxiang", "fragment =" + fragment.getClass().getSimpleName());
        fragmentTransaction.replace(fragmentContent, fragment, fragment.getClass().getSimpleName());
        if(isAddToBack)
        {
            baseFragmentList.push(fragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    };

    //此方法是为给在xml中定义好FrameLayout添加对应布局使用
    public void replaceFragment(int id, BaseFragment fragment, boolean isAddToBack)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.e("zhaxiang", "fragment =" + fragment.getClass().getSimpleName());
        fragmentTransaction.replace(id, fragment, fragment.getClass().getSimpleName());
        if(isAddToBack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    };

    //返回true 表示pop fragment成功   false 表示没有可以pop的fragment 会直接导致调用activity的onBackPressed()
    public boolean popFragment()
    {
        if(baseFragmentList.size() > 0)
        {
            if(!baseFragmentList.peek().actionbarClickListener(CustomActionBar.LEFT))
            {
                baseFragmentList.pop();
                return fragmentManager.popBackStackImmediate();
            }
            else
            {
                return true;//TODO:此处暂时不做处理 留给fragment自己处理
            }
        }
        return false;
    }

    public boolean popFragmentWithList()
    {
        if(baseFragmentList.size() > 0)
        {
            baseFragmentList.pop();
            return fragmentManager.popBackStackImmediate();
        }
        return false;
    }
}
