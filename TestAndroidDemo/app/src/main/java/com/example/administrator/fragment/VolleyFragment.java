package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.BaseApplication;
import com.example.administrator.data.StringObj;
import com.example.administrator.utils.Constants;
import com.example.administrator.volley.MyVolleyListener;
import com.example.administrator.data.Weather;
import com.example.administrator.testandroiddemo.R;

import java.util.Objects;

/**
 * Created by Administrator on 2015/12/10.
 */
public class VolleyFragment extends BaseFragment
{
    private final String TAG = VolleyFragment.class.getSimpleName();

    private Button imageUpdateBtn = null;
    private Button xmlBtn = null;
    private Button jsonBtn = null;
    private TextView responseText = null;

    @Override
    protected void findViews(View v)
    {
        imageUpdateBtn = (Button)v.findViewById(R.id.imageViewBtn);
        xmlBtn = (Button)v.findViewById(R.id.xmlBtn);
        jsonBtn = (Button)v.findViewById(R.id.jsonBtn);
        responseText = (TextView)v.findViewById(R.id.responseContent);
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

        xmlBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BaseApplication.application.getVolleyManager().XMLRequest(Constants.testUrl, new MyVolleyListener<Weather>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onResult(String code, String message, Weather a) {
                        if(code.equals("0"))
                        {
                            responseText.setText(a.weatherToString());
                        }
                        else
                        {
                            responseText.setText("response fail!");
                        }
                    }

                });
            }
        });

        jsonBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BaseApplication.application.getVolleyManager().JsonObjectRequest(Constants.testJsonUrl, new MyVolleyListener<StringObj>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onResult(String code, String message, StringObj a) {
                        if(a != null)
                        responseText.setText(a.getContent());
                    }
                });
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
