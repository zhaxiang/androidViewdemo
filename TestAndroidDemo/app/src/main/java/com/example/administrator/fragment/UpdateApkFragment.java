package com.example.administrator.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.testandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateApkFragment extends BaseFragment implements View.OnClickListener
{
    private final String TAG = UpdateApkFragment.class.getSimpleName();

    private Button downloadBtn;
    private Button pauseBtn;
    private Button deleteBtn;
    private TextView downloadText;

    public UpdateApkFragment()
    {
        // Required empty public constructor
    }


    @Override
    protected void findViews(View v)
    {
        downloadBtn = (Button)v.findViewById(R.id.downloadBtn);
        pauseBtn = (Button)v.findViewById(R.id.pauseBtn);
        deleteBtn = (Button)v.findViewById(R.id.deleteBtn);
        downloadText = (TextView)v.findViewById(R.id.downloadText);
    }

    @Override
    protected void initViews(Bundle var)
    {
        downloadBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle var)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_apk, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onClick(View v)
    {
        if(v == downloadBtn)
        {
            Log.v(TAG, "downloadBtn");
        }
        else if(v == pauseBtn)
        {
            Log.v(TAG, "pauseBtn");
        }
        else if(v == deleteBtn)
        {
            Log.v(TAG, "deleteBtn");
        }
    }
}
