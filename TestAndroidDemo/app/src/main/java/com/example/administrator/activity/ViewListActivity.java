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
import com.example.administrator.dialog.CommonDialog;
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
    private Button eventBusBtn = null;
    private Button slidingPaneLayoutBtn = null;
    private Button updateApkBtn = null;
    private Button gridviewBtn = null;
    private Button percentLayoutBtn = null;
    private Button nextBtn = null;
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

        eventBusBtn = (Button)findViewById(R.id.eventBusBtn);
        eventBusBtn.setOnClickListener(this);

        slidingPaneLayoutBtn = (Button)findViewById(R.id.slidingPaneLayoutBtn);
        slidingPaneLayoutBtn.setOnClickListener(this);

        updateApkBtn = (Button)findViewById(R.id.updateApkBtn);
        updateApkBtn.setOnClickListener(this);

        gridviewBtn = (Button)findViewById(R.id.gridviewBtn);
        gridviewBtn.setOnClickListener(this);

        percentLayoutBtn = (Button)findViewById(R.id.percentLayoutBtn);
        percentLayoutBtn.setOnClickListener(this);

        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);


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
        else if(v == eventBusBtn)
        {
            Log.v(TAG, "eventBusBtn");
            tagName = Constants.EventBusFragment;
        }
        else if(v == slidingPaneLayoutBtn)
        {
            Log.v(TAG, "slidingPaneLayoutBtn");
            tagName = Constants.SlidingPaneLayoutFragment;
        }
        else if(v == updateApkBtn)
        {
            Log.v(TAG, "UpdateApkFragment");
            tagName = Constants.UpdateApkFragment;
        }
        else if(v == gridviewBtn)
        {
            Log.v(TAG, "GridViewFragment");
            tagName = Constants.GridViewFragment;
        }
        else if(v == percentLayoutBtn)
        {
            Log.v(TAG, "PercentlayoutFragment");
            tagName = Constants.PercentlayoutFragment;
        }
        else if(v == nextBtn)
        {
            CommonDialog commonDialog = CommonDialog.create(this);
            commonDialog.setMessage("测试");
            commonDialog.setCenterBtnClick(new CommonDialog.ButtonClick() {
                @Override
                public void onClick() {

                }
            });
            commonDialog.show();
            return;
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
