package com.example.administrator.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.data.AppInfo;
import com.example.administrator.fragment.adapter.GridViewAdapter;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.testandroiddemo.service.ServiceManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridViewFragment extends BaseFragment
{
    private String TAG = GridViewFragment.class.getSimpleName();

    private GridView gridView;

    public GridViewFragment()
    {
        // Required empty public constructor
    }


    @Override
    protected void findViews(View v)
    {
        gridView = (GridView)v.findViewById(R.id.gridview);
    }

    @Override
    protected void initViews(Bundle var)
    {
        List<AppInfo> appInfoList = ServiceManager.getInstance().getAppInfos();
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), appInfoList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.e(TAG, "position =" + position);
            }
        });
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
        return inflater.inflate(R.layout.fragment_grid_view, container, false);
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
}
