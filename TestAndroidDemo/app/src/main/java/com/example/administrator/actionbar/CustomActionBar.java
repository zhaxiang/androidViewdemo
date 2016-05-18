package com.example.administrator.actionbar;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.testandroiddemo.R;
import com.example.administrator.view.CustomButton;

/**
 * Created by Administrator on 2016/1/28.
 */

//使用actionBar必须要targetSdkVersion不低于11
//通过XML文件来实现Action Item，一定要自定义命名空间，
//而且该命名空间的后缀一定要和item中showAsAction的前缀一致，本例中为actionbar
//详情看res/menu/menu
public class CustomActionBar implements View.OnClickListener
{
//    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//    actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME
//    | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
//    actionBar.setHomeButtonEnabled(true);//设置导航左侧有点击事件
//    actionBar.setHomeAsUpIndicator(R.mipmap.leftarrow);//设置左侧的图标
//    actionBar.setDisplayHomeAsUpEnabled(true);//显示左侧的图标

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.actionbar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
////            case R.id.menu_settings:
////                Log.e("zhaxaing", "onOptionsItemSelected menu_settings");
////                break;
//
//            case R.id.menu_load:
//                Log.e("zhaxaing", "onOptionsItemSelected menu_load");
//                break;
//            case android.R.id.home:
//                Log.e("zhaxaing", "onOptionsItemSelected home");
//                break;
//        }
//
//
//        return true;
//    }

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public ActionBar actionBar = null;

    private CustomButton btn_left = null;
    private CustomButton btn_right = null;
    private TextView text_title = null;

    private ActionbarClickListener actionbarClickListener = null;

    public CustomActionBar(ActionBar actionBar)
    {
        this.actionBar = actionBar;
        setContentView(R.layout.actionbar_custom);
    }

    private void setContentView(int id)
    {
        if(actionBar != null)
        {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(id);
            findView(actionBar.getCustomView());
        }
    }

    private void findView(View v)
    {
        btn_left = (CustomButton)v.findViewById(R.id.btn_left);
        btn_right = (CustomButton)v.findViewById(R.id.btn_right);
        text_title = (TextView)v.findViewById(R.id.text_title);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        btn_left.setVisibility(View.INVISIBLE);
        btn_right.setVisibility(View.INVISIBLE);
    }

    public void setTitle(String content)
    {
        if(text_title != null)
        {
            text_title.setText(content);
        }
    }

    public void setTitle(int id)
    {
        if(text_title != null)
        {
            text_title.setText(id);
        }
    }

    public void setRightImage(int id)
    {
        if(btn_right != null)
        {
            btn_right.setVisibility(View.VISIBLE);
            btn_right.setBackgroundResource(id);
        }
    }

    public void setLeftImage(int id)
    {
        if(btn_left != null)
        {
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setBackgroundResource(id);
        }
    }

    public void setCustomActionBarListener(ActionbarClickListener listener)
    {
        actionbarClickListener = listener;
    }

    @Override
    public void onClick(View v)
    {
        if(actionbarClickListener != null)
        {
            int id = -1;
            if(v == btn_left)
            {
                id = LEFT;
            }
            else if(v == btn_right)
            {
                id = RIGHT;
            }
            actionbarClickListener.onClick(id);
        }

    }

    public interface ActionbarClickListener
    {
        public void onClick(int v);
    }
}
