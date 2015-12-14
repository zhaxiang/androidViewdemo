package com.example.administrator.fragment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.BaseApplication;
import com.example.administrator.data.AppInfo;
import com.example.administrator.testandroiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class AppInfoAdapter extends BaseAdapter
{
    public List<AppInfo> getInfos()
    {
        return infos;
    }

    private List<AppInfo> infos;
    private LayoutInflater layoutInflater;
    private Context context;

    public AppInfoAdapter(Context context, List<AppInfo> infos)
    {
        this.context = context;
        this.infos = new ArrayList<AppInfo>();
        for(int i = 0; i < infos.size(); i++)
        {
            this.infos.add(new AppInfo(infos.get(i)));
        }
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return infos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AppInfoItem infoItem = null;
        if(null == convertView)
        {
            infoItem = new AppInfoItem();
            convertView = layoutInflater.inflate(R.layout.auto_list_item, null);
            infoItem.imageView = (ImageView)convertView.findViewById(R.id.iconImageView);
            infoItem.textView = (TextView)convertView.findViewById(R.id.appNameLabel);
            convertView.setTag(infoItem);
        }
        else
        {
            infoItem = (AppInfoItem)convertView.getTag();
        }

        String url = infos.get(position).getAppUrl();
        if(null != url)
        {
            if(!url.equals(""))
                BaseApplication.application.getVolleyManager().getImageByUrl(url, infoItem.imageView);
        }
        else
        {
            //绑定数据
            String path = infos.get(position).getAppIconPath();
            if(null != path)
            {
                if(!path.equals(""))
                {
                    Bitmap iconBm = BitmapFactory.decodeFile(path);
                    if(null != iconBm)
                        infoItem.imageView.setImageBitmap(iconBm);
                }
            }
            else
            {
                infoItem.imageView.setImageResource(R.mipmap.ic_launcher);
            }
        }

        infoItem.textView.setText(infos.get(position).getAppName());

        return convertView;
    }

    public class AppInfoItem
    {
        public ImageView imageView;
        public TextView textView;
    }
}
