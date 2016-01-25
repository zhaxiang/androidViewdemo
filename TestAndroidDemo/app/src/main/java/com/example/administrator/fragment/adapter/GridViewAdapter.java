package com.example.administrator.fragment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.data.AppInfo;
import com.example.administrator.testandroiddemo.R;
import com.example.administrator.utils.UtilsMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
public class GridViewAdapter extends BaseAdapter
{

    public List<AppInfo> getInfos()
    {
        return infos;
    }

    private List<AppInfo> infos;
    private Context context;

    public GridViewAdapter(Context context, List<AppInfo> infos)
    {
        this.context = context;
        this.infos = infos;
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
        GridviewItem item = null;
        if(convertView == null)
        {
            item = new GridviewItem();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
            item.imageView = (ImageView)convertView.findViewById(R.id.gridview_image);
            item.tipLabel = (TextView)convertView.findViewById(R.id.gridview_lable_tip);
            item.detailLabel = (TextView)convertView.findViewById(R.id.gridview_lable_detail);

            convertView.setTag(item);
        }
        else
        {
            item = (GridviewItem)convertView.getTag();
        }

        Log.e("zhaxiang", "parent.width =" + parent.getWidth() / 3);
        convertView.setLayoutParams(new ViewGroup.LayoutParams((int) (parent.getWidth() / 3),
                (int) (parent.getWidth() / 3)));

        AppInfo info = infos.get(position);
        String path = info.getAppIconPath();
        if(null != path)
        {
            if(!path.equals(""))
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                int be = (int)(options.outHeight / (float)100);
                if (be <= 0)
                    be = 1;
                options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
                Bitmap bitmap=BitmapFactory.decodeFile(path,options);
                BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
                item.imageView.setBackground(bd);
            }
        }
        else
        {
            item.imageView.setBackgroundResource(R.drawable.ic_launcher);
        }
        item.tipLabel.setText(info.getAppName());
        item.detailLabel.setText(info.getAppName());

        return convertView;
    }

    public class GridviewItem
    {
        public ImageView imageView;
        public TextView tipLabel;
        public TextView detailLabel;
    }
}
