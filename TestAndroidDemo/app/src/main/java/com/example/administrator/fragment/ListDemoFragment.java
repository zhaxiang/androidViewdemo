package com.example.administrator.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.BaseApplication;
import com.example.administrator.actionbar.CustomActionBar;
import com.example.administrator.data.AppInfo;
import com.example.administrator.fragment.adapter.AppInfoAdapter;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.view.AutoListView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class ListDemoFragment extends BaseFragment
{
    private final String TAG = ListDemoFragment.class.getSimpleName();
    private AutoListView listView;
    private AppInfoAdapter appInfoAdapter;

    @Override
    protected void findViews(View v)
    {
        listView = (AutoListView)v.findViewById(R.id.listview);
    }

    static int mIndex = 0;
    static int pullIndex = 0;
    @Override
    protected void initViews(Bundle var)
    {

        appInfoAdapter = new AppInfoAdapter(getActivity(), BaseApplication.application.getServiceManager().getAppInfos());

        listView.setAdapter(appInfoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.v(TAG, "position =" + position);
                Toast.makeText(getActivity().getApplicationContext(), "current item pos index = " +  position, Toast.LENGTH_SHORT).show();
            }
        });
//        listView.setPageSize();
//        listView.setResultSize();
        listView.setOnLoadListener(new AutoListView.OnLoadListener()
        {
            @Override
            public void onLoad()
            {

                new AsyncTask<Void, Void, Void>()
                {

                    @Override
                    protected Void doInBackground(Void... params)
                    {
                            SystemClock.sleep(5000);

                        List<AppInfo> appInfos = appInfoAdapter.getInfos();
                        for(int i = 0; i < 10; i++)
                        {
                            AppInfo info = new AppInfo(appInfos.get(i));
                            info.setAppName("load  new mindex =" + mIndex + "  " + info.getAppName());
                            appInfoAdapter.getInfos().add(info);
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid)
                    {
                        super.onPostExecute(aVoid);
                        mIndex++;
                        appInfoAdapter.notifyDataSetChanged();
                        if(mIndex == 1)
                        {
                            listView.setResultSize(10);
                        }
                        else if(mIndex == 2)
                        {
                            listView.setResultSize(0);
                        }
                        listView.onLoadComplete();

                    }
                }.execute(new Void[]{});
            }
        });

        listView.setOnRefreshListener(new AutoListView.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new AsyncTask<Void, Void, Void>()
                {

                    @Override
                    protected Void doInBackground(Void... params)
                    {
                        SystemClock.sleep(2000);

                        for(int i = 0; i < 10; i++)
                        {
                            AppInfo info = appInfoAdapter.getInfos().get(i);
                            info.setAppName("pull  new pullIndex =" + pullIndex + "  " + info.getAppName());
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid)
                    {
                        super.onPostExecute(aVoid);
                        appInfoAdapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                        pullIndex++;
                    }
                }.execute(new Void[]{});
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

        getCustomActionBar().setLeftImage(R.drawable.leftarrow);
        getCustomActionBar().setCustomActionBarListener(new CustomActionBar.ActionbarClickListener()
        {
            @Override
            public void onClick(int v)
            {
                if(v == CustomActionBar.LEFT)
                {
                    onBackPressed();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_list_demo, null);
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
