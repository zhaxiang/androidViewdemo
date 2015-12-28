package com.example.administrator.fragment.SlidingPaneLayout;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.administrator.fragment.BaseFragment;
import com.example.administrator.testandroiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlidingLeftFragment extends BaseFragment
{
    private ListView listView;
    private CheckBox animCheck;

    public interface SlidingLeftListener
    {
        public void onLeftChang(String url);
    }


    public SlidingLeftListener slidingLeftListener;

    public void setSlidingLeftListener(SlidingLeftListener slidingLeftListener)
    {
        this.slidingLeftListener = slidingLeftListener;
    }

    public SlidingLeftFragment()
    {
        // Required empty public constructor
    }

    private List<String> getData()
    {

        List<String> data = new ArrayList<String>();
        data.add(getActivity().getResources().getString(R.string.wangyi));
        data.add(getActivity().getResources().getString(R.string.tengxun));
        data.add(getActivity().getResources().getString(R.string.xinlang));
        data.add(getActivity().getResources().getString(R.string.souhu));

        return data;
    }

    public boolean isAnimation()
    {
        return animCheck.isChecked();
    }

    @Override
    protected void findViews(View v)
    {
        listView = (ListView)v.findViewById(R.id.listview);
        animCheck = (CheckBox)v.findViewById(R.id.animCheck);
    }

    @Override
    protected void initViews(Bundle var)
    {
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String url = "http://www.163.com";
                switch (position)
                {
                    case 0:
                        url = "http://www.163.com";
                        break;
                    case 1:
                        url = "http://www.qq.com";
                        break;
                    case 2:
                        url = "http://www.sina.com";
                        break;
                    case 3:
                        url = "http://www.sohu.com";
                        break;

                }
                if(null != slidingLeftListener)
                    slidingLeftListener.onLeftChang(url);
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
        return inflater.inflate(R.layout.fragment_sliding_pane_left, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
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
    }
}
