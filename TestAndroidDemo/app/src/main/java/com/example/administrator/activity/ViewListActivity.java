package com.example.administrator.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.BaseApplication;
import com.example.administrator.data.Constants;
import com.example.administrator.testandroiddemo.R;

/**
 * Created by Administrator on 2015/12/7.
 */
public class ViewListActivity extends AppCompatActivity implements View.OnClickListener
{
    private final String TAG = ViewListActivity.class.getSimpleName();
    private Button listBtn = null;
    private Button volleyBtn = null;
    private Button gaodeMapBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        listBtn = (Button)findViewById(R.id.list_show);
        listBtn.setOnClickListener(this);

        volleyBtn = (Button)findViewById(R.id.volley_show);
        volleyBtn.setOnClickListener(this);

        gaodeMapBtn = (Button)findViewById(R.id.gaoDeDitu);
        gaodeMapBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        String tagName = "";
        if(v == listBtn)
        {
            Log.v(TAG, "listBtn");
            tagName = Constants.ListFragment;
        }
        else if(v == volleyBtn)
        {
            Log.v(TAG, "volleyBtn");
            tagName = Constants.VolleyFragment;
        }
        else if(v == gaodeMapBtn)
        {
            Log.v(TAG, "gaodeMapBtn");
            tagName = Constants.GaodeMapFragment;
        }
        BaseApplication.application.setFragmentString(tagName);
        Intent intent = new Intent(ViewListActivity.this, ShowDemoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
        BaseApplication.application.getServiceManager().unBindService();
    }
}
