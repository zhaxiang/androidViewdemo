package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.BaseApplication;
import com.example.administrator.testandroiddemo.R;

/**
 * Created by Administrator on 2015/12/10.
 */
public class VolleyFragment extends BaseFragment
{
    private final String TAG = VolleyFragment.class.getSimpleName();

    private Button imageUpdateBtn = null;
    private Button jsonBtn = null;

    @Override
    protected void findViews(View v)
    {
        imageUpdateBtn = (Button)v.findViewById(R.id.imageViewBtn);
        jsonBtn = (Button)v.findViewById(R.id.jsonBtn);
    }

    @Override
    protected void initViews(Bundle var)
    {
        imageUpdateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "imageUpdateBtn");
                replaceFragment(new VolleyImageFragment(), true);
            }
        });

        jsonBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BaseApplication.application.getVolleyManager().JsonObjectRequestByGet();
            }
        });
    }

    @Override
    protected void initData(Bundle var)
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_volley, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }
}
