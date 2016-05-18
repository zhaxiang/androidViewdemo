package com.example.administrator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.activity.BaseActivity;

public abstract class BaseFragment extends Fragment
{
    private final  String TAG = BaseFragment.class.getSimpleName();

    protected abstract void findViews(View v);

    protected abstract void initViews(Bundle var);

    protected abstract void initData(Bundle var);

    private CustomActionBar customActionBar = null;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        customActionBar = ((BaseActivity)this.getActivity()).getCustomActionBar();
    }

    public CustomActionBar getCustomActionBar()
    {
        return customActionBar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(TAG, "onViewStateRestored");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.v(TAG, "onDetach");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void replaceFragment(BaseFragment fragment, boolean isAddToStack)
    {
        if(this.getActivity() == null)
        {
            throw new IllegalStateException("getActivity is null");
        }

        CustomerFragmentManager customerFragmentManager = ((BaseActivity) this.getActivity()).getCustomerFragmentManager();
        if(null != customerFragmentManager)
        {
            Log.v(TAG, "replaceFragment null != customerFragmentManager");
            customerFragmentManager.replaceFragment(fragment, isAddToStack);
        }
    }

    public void replaceFragment(int id, BaseFragment fragment, boolean isAddToStack)
    {
        if(this.getActivity() == null)
        {
            throw new IllegalStateException("getActivity is null");
        }

        CustomerFragmentManager customerFragmentManager = ((BaseActivity) this.getActivity()).getCustomerFragmentManager();
        if(null != customerFragmentManager)
        {
            Log.v(TAG, "replaceFragment null != customerFragmentManager");
            customerFragmentManager.replaceFragment(id, fragment, isAddToStack);
        }
    }

    public void onBackPressed()
    {
        if(this.getActivity() == null)
        {
            throw new IllegalStateException("getActivity is null");
        }
        ((BaseActivity)getActivity()).onBackPressed();
    }

    public void popFragment()
    {
        if(this.getActivity() == null)
        {
            throw new IllegalStateException("getActivity is null");
        }
        CustomerFragmentManager customerFragmentManager = ((BaseActivity) this.getActivity()).getCustomerFragmentManager();
        if(null != customerFragmentManager)
            customerFragmentManager.popFragment();
    }

    public boolean actionbarClickListener(int id) //CustomActionBar.LEFT   CustomActionBar.RIGHT
    {
        Log.e(TAG, "actionbarClickListener ");
        return false;
    }

    public void showProgressDialog(int id)
    {
        ((BaseActivity)this.getActivity()).showProgressDialog(id);
    }

//    public void showProgressDialog(String text)
//    {
//        ((BaseActivity)this.getActivity()).showProgressDialog(text);
//    }
//
//    public void showProgressDialog(int id, String text)
//    {
//
//    }

    public void dismissProgressDialog()
    {
        ((BaseActivity)this.getActivity()).dissmissProgressDialog();
    }

    public void showToast(int id)
    {
        ((BaseActivity)this.getActivity()).showToast(id);
    }

    public void showToast(String content)
    {
        ((BaseActivity)this.getActivity()).showToast(content);
    }

    public void showToast(int id, String content)
    {
        ((BaseActivity)this.getActivity()).showToast(id, content);
    }
}
