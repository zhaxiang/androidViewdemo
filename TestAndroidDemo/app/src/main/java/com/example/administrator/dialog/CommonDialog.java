package com.example.administrator.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.testandroiddemo.R;
import com.example.administrator.view.CustomButton;

/**
 * Created by Administrator on 2016/1/28.
 */
public class CommonDialog extends Dialog
{

    private LinearLayout linear_tip = null;
    private TextView text_tip = null;
    private TextView text_content = null;
    private CustomButton leftBtn = null;
    private CustomButton centerBtn = null;
    private CustomButton rightBtn = null;

    public CommonDialog(Context context)
    {
        super(context);
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }

    public CommonDialog(Context context, int themeResId)
    {
        super(context, themeResId);


        this.setContentView(R.layout.dialog_common);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == android.view.KeyEvent.KEYCODE_SEARCH || keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        findview();
    }

    public static CommonDialog create(Context context)
    {
        return new CommonDialog(context, R.style.CenterDialog);
    }

    private void findview()
    {
        linear_tip =  (LinearLayout)this.findViewById(R.id.linear_tip);
        text_tip = (TextView)this.findViewById(R.id.text_tip);
        text_content = (TextView)this.findViewById(R.id.text_content);
        leftBtn = (CustomButton)this.findViewById(R.id.leftBtn);
        centerBtn = (CustomButton)this.findViewById(R.id.centerBtn);
        rightBtn = (CustomButton)this.findViewById(R.id.rightBtn);

        linear_tip.setVisibility(View.GONE);
        leftBtn.setVisibility(View.INVISIBLE);
        centerBtn.setVisibility(View.INVISIBLE);
        rightBtn.setVisibility(View.INVISIBLE);
    }

    public void setTextTip(String content)
    {
        if(linear_tip.getVisibility() != View.VISIBLE)
            linear_tip.setVisibility(View.VISIBLE);
        text_tip.setText(content);
    }

    public void setTextTip(int id)
    {
        if(linear_tip.getVisibility() != View.VISIBLE)
            linear_tip.setVisibility(View.VISIBLE);
        text_tip.setText(id);
    }

    public void setMessage(String content)
    {
        text_content.setText(content);
    }

    public void setMessage(int id)
    {
        text_content.setText(id);
    }

    public void setLeftBtnClick(final ButtonClick btnClick)
    {
        if(leftBtn.getVisibility() != View.VISIBLE)
            leftBtn.setVisibility(View.VISIBLE);

        leftBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();

                if (btnClick != null)
                    btnClick.onClick();
            }
        });
    }

    public void setCenterBtnClick(final ButtonClick btnClick)
    {
        if(centerBtn.getVisibility() != View.VISIBLE)
            centerBtn.setVisibility(View.VISIBLE);

        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if (btnClick != null)
                    btnClick.onClick();
            }
        });
    }

    public void setRightBtnClick(final ButtonClick btnClick)
    {
        if(rightBtn.getVisibility() != View.VISIBLE)
            rightBtn.setVisibility(View.VISIBLE);

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if (btnClick != null)
                    btnClick.onClick();
            }
        });
    }

    public interface ButtonClick
    {
        public void onClick();
    }
}
