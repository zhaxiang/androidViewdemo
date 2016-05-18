package com.example.administrator.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/12/30.
 */
public class CustomImageView extends ImageView
{
    public CustomImageView(Context context)
    {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
//            this.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            this.getBackground().setAlpha(125);

        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(!this.getBackground().getBounds().contains((int)event.getX(), (int)event.getY()))
            {
//                this.getBackground().clearColorFilter();
                this.getBackground().setAlpha(255);
            }

        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
//            this.getBackground().clearColorFilter();
            this.getBackground().setAlpha(255);
        }
        else
        {
//            this.getBackground().clearColorFilter();
            this.getBackground().setAlpha(255);
        }
        return super.onTouchEvent(event);
    }


}
