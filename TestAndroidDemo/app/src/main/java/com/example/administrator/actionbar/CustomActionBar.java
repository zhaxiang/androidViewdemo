package com.example.administrator.actionbar;

import android.app.ActionBar;

/**
 * Created by Administrator on 2016/1/28.
 */

//使用actionBar必须要targetSdkVersion不低于11
//通过XML文件来实现Action Item，一定要自定义命名空间，
//而且该命名空间的后缀一定要和item中showAsAction的前缀一致，本例中为actionbar
//详情看res/menu/menu
public class CustomActionBar
{
    public ActionBar actionBar = null;

    public CustomActionBar(ActionBar actionBar)
    {
        this.actionBar = actionBar;
    }

    public void setContentView(int id)
    {
        if(this.actionBar != null)
        {
            this.actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.actionBar.setCustomView(id);
        }
    }
}
