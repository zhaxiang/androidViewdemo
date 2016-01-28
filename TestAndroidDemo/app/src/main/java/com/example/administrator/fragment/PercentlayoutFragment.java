package com.example.administrator.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.testandroiddemo.R;
import com.zhy.android.percent.support.PercentRelativeLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class PercentlayoutFragment extends BaseFragment implements View.OnClickListener
{
    private PercentRelativeLayout percentRelativeLayout_1;
    private PercentRelativeLayout percentRelativeLayout_2;
    private PercentRelativeLayout percentRelativeLayout_3;

    public PercentlayoutFragment()
    {
        // Required empty public constructor
    }


    @Override
    protected void findViews(View v)
    {
        percentRelativeLayout_1 = (PercentRelativeLayout)v.findViewById(R.id.percentRelativeLayout_1);
        percentRelativeLayout_2 = (PercentRelativeLayout)v.findViewById(R.id.percentRelativeLayout_2);
        percentRelativeLayout_3 = (PercentRelativeLayout)v.findViewById(R.id.percentRelativeLayout_3);

        percentRelativeLayout_1.setOnClickListener(this);
        percentRelativeLayout_2.setOnClickListener(this);
        percentRelativeLayout_3.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle var) {

    }

    @Override
    protected void initData(Bundle var) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_test_percent, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
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
        String content = null;

        if(v == percentRelativeLayout_1)
        {
            content = "percentRelativeLayout_1";
        }
        else if(v == percentRelativeLayout_2)
        {
            content = "percentRelativeLayout_2";
        }
        else if(v == percentRelativeLayout_3)
        {
            content = "percentRelativeLayout_3";
        }

        showToast(content);
    }
}
