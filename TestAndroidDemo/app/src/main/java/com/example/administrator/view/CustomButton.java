package com.example.administrator.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2015/12/30.
 */
public class CustomButton extends Button
{
    public CustomButton(Context context)
    {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            this.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(!this.getBackground().getBounds().contains((int)event.getX(), (int)event.getY()))
            {
                this.getBackground().clearColorFilter();
            }

        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            this.getBackground().clearColorFilter();
        }
        else
        {
            this.getBackground().clearColorFilter();
        }
        return super.onTouchEvent(event);
    }


}
