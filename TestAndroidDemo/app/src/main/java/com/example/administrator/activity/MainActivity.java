package com.example.administrator.activity;

import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.BaseApplication;
import com.example.administrator.data.Person;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.testandroiddemo.service.IBaseServiceCallback;

public class MainActivity extends AppCompatActivity//AppCompatActivity中实现了actionbar
{
    private final String TAG = MainActivity.class.getSimpleName();
    private Button testBtn = null;
    private Button testPersonBtn = null;
    private Button testDownloadImageBtn = null;
    private Button personBtn = null;
    private Button inBtn = null;
    private Button outBtn = null;
    private Button inOutBtn = null;
    private Button viewDemoBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBtn = (Button)findViewById(R.id.testBtn);
        testPersonBtn = (Button)findViewById(R.id.testPersonBtn);
        personBtn = (Button)findViewById(R.id.getPersonBtn);
        testDownloadImageBtn = (Button)findViewById(R.id.testDownloadImage);

        inBtn = (Button)findViewById(R.id.testInBtn);
        outBtn = (Button)findViewById(R.id.testOutBtn);
        inOutBtn = (Button)findViewById(R.id.testInOutBtn);

        viewDemoBtn = (Button)findViewById(R.id.viewDemo);

        testBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "testBtn onClick");

                BaseApplication.application.getServiceManager().test("hahhaha");
            }
        });

        testPersonBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "testPersonBtn onClick");
                ((BaseApplication)getApplication()).getServiceManager().testPerson(new Person("michael", "descrip", 2));
            }
        });

        personBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "personBtn onClick");
                ((BaseApplication)getApplication()).getServiceManager().getPersonTest();
            }
        });

        testDownloadImageBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "testPersonBtn onClick");
                BaseApplication.application.getServiceManager().dowmloadImage("testurl", new IBaseServiceCallback.Stub() {
                    @Override
                    public void testCallback(String str) throws RemoteException {
                        Log.v(TAG, "testCallback onClick str =" + str);
                    }
                });
            }
        });

        inBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "inBtn onClick");
                Person before = new Person("zhaxiang", "dec", 1);
                Person afterPer = BaseApplication.application.getServiceManager().testPersonIn(before);
                Log.v(TAG, "inBtn onClick afterPer.name = " + afterPer.getName());
                Log.v(TAG, "inBtn onClick before.name = " + before.getName());
            }
        });

        outBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "outBtn onClick");
                Person before = new Person("zhaxiang", "dec", 1);
                Person afterPer = BaseApplication.application.getServiceManager().testPersonOut(before);
                Log.v(TAG, "outBtn onClick afterPer.name = " + afterPer.getName());
                Log.v(TAG, "outBtn onClick before.name = " + before.getName());
            }
        });

        inOutBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "inOutBtn onClick");
                Person before = new Person("zhaxiang", "dec", 1);
                Person afterPer = BaseApplication.application.getServiceManager().testPersonInOut(before);
                Log.v(TAG, "inOutBtn onClick afterPer.name = " + afterPer.getName());
                Log.v(TAG, "inOutBtn onClick before.name = " + before.getName());
            }
        });

        viewDemoBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ViewListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        Log.v(TAG, "onResume");
        //第一次初始化，启动service后台获取所有应用
        BaseApplication.application.getServiceManager();
        BaseApplication.application.getVolleyManager();
        super.onResume();
    }
}
