package com.example.administrator.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.BaseApplication;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.fragment.ListDemoFragment;

public class ShowDemoActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setTitle(R.string.list_refresh_show);
        setContentView(R.layout.activity_show_demo);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListDemoFragment listDemoFragment = new ListDemoFragment();
        fragmentTransaction.replace(R.id.main_frament, listDemoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
