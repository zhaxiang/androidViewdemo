package com.example.administrator.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.activity.BaseActivity;
import com.example.administrator.testandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionbarFragment extends BaseFragment
{
    CustomActionBar customActionBar = null;

    private TextView text_left = null;
    private TextView text_right = null;

    public ActionbarFragment()
    {
        // Required empty public constructor
    }


    @Override
    protected void findViews(View v)
    {
        text_left = (TextView)v.findViewById(R.id.text_left);
        text_right = (TextView)v.findViewById(R.id.text_right);
    }

    @Override
    protected void initViews(Bundle var)
    {

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
        return inflater.inflate(R.layout.fragment_actionbar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getCustomActionBar().setCustomActionBarListener(new CustomActionBar.ActionbarClickListener()
        {
            @Override
            public void onClick(int v)
            {
                if(v == CustomActionBar.LEFT)
                {
                    text_left.setText("Actionbar left click");
                    popFragment();
                }
                else if (v == CustomActionBar.RIGHT)
                {
                    text_right.setText("Actionbar right click");
                }
            }
        });

        customActionBar.setTitle(R.string.actionbar_custom_test);
    }
}
