package com.example.administrator.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.data.Constants;
import com.example.administrator.data.EventBusCustomData;
import com.example.administrator.testandroiddemo.R;

import de.greenrobot.event.EventBus;

public class EventBusPostActivity extends BaseActivity
{
    private Button testBtn = null;
    private TextView tipText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_post);

//        getSupportActionBar().setTitle(R.string.event_bus_demo);

        tipText = (TextView)findViewById(R.id.tip);
        testBtn = (Button)findViewById(R.id.testBtn);

        testBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tipText.setVisibility(View.VISIBLE);
                tipText.setText("used eventbus, please press back to see");
                EventBus.getDefault().post(new EventBusCustomData("use eventbus post data test"));
            }
        });
    }
}
