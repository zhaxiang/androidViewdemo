package com.example.administrator.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.BaseApplication;
import com.example.administrator.activity.EventBusPostActivity;
import com.example.administrator.data.EventBusCustomData;
import com.example.administrator.testandroiddemo.R;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2015/12/22.
 */
public class EventBusFragment extends BaseFragment
{
    private final String TAG = EventBusFragment.class.getSimpleName();

    private Button jumpBtn = null;
    private TextView contentText = null;

    @Override
    protected void findViews(View v)
    {
        EventBus.getDefault().register(this);

        jumpBtn = (Button)v.findViewById(R.id.jump);
        contentText = (TextView)v.findViewById(R.id.eventContent);
    }

    @Override
    protected void initViews(Bundle var)
    {
        jumpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EventBusPostActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle var)
    {

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
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
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
        return inflater.inflate(R.layout.fragment_eventbus, null);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }

    public void onEventMainThread(EventBusCustomData event)
    {
        Log.v(TAG, "onEventMainThread");
        if(null != event)
        {
            String msg = "onEventMainThread收到了消息：" + event.getContent();
            contentText.setText(msg);
            BaseApplication.application.showToast(msg);
        }
    }
}
