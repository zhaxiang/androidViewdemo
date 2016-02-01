package com.example.administrator.fragment.SlidingPaneLayout;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.administrator.fragment.BaseFragment;
import com.example.administrator.testandroiddemo.R;

public class SlidingPaneLayoutFragment extends BaseFragment implements SlidingLeftFragment.SlidingLeftListener
{
    private SlidingPaneLayout slidingPaneLayout;
    private SlidingRightFragment slidingRightFragment;
    private SlidingLeftFragment slidingLeftFragment;
    private View mMenuPanel;

    @Override
    protected void findViews(View v)
    {
        slidingPaneLayout = (SlidingPaneLayout)v.findViewById(R.id.slidingPaneLayout);

//        Fragment leftFragment = (Fragment)v.findViewById(R.id.leftFragment);
        slidingLeftFragment = new SlidingLeftFragment();
        addFragment(R.id.leftFragment, slidingLeftFragment, false);

        slidingRightFragment = new SlidingRightFragment();
        addFragment(R.id.rightFragment, slidingRightFragment, false);

//        slidingLeftFragment = (SlidingLeftFragment)findFragmentById(R.id.leftFragment);
//        slidingRightFragment = (SlidingRightFragment)findFragmentById(R.id.rightFragment);
    }

    @Override
    protected void initViews(Bundle var)
    {
        slidingLeftFragment.setSlidingLeftListener(this);

        slidingPaneLayout.openPane();
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener()
        {
            @Override
            public void onPanelSlide(View panel, float slideOffset)
            {
                if(slidingLeftFragment.isAnimation())
                {
                    if (mMenuPanel == null)
                    {
                        final int childCount = slidingPaneLayout.getChildCount();
                        for (int i = 0; i < childCount; i++)
                        {
                            final View child = slidingPaneLayout.getChildAt(i);
                            if (child != panel) {
                                mMenuPanel = child;
                                break;
                            }
                        }
                    }
                    final float scaleLeft = 1 - 0.3f * (1 - slideOffset);
                    mMenuPanel.setPivotX(-0.3f * mMenuPanel.getWidth());
                    mMenuPanel.setPivotY(mMenuPanel.getHeight() / 2f);
                    mMenuPanel.setScaleX(scaleLeft);
                    mMenuPanel.setScaleY(scaleLeft);

                    final float scale = 1 - 0.2f * slideOffset;
                    panel.setPivotX(0);
                    panel.setPivotY(panel.getHeight() / 2.0f);
                    panel.setScaleX(scale);
                    panel.setScaleY(scale);
                }
            }

            @Override
            public void onPanelOpened(View panel)
            {
                slidingLeftFragment.setHasOptionsMenu(false);
            }

            @Override
            public void onPanelClosed(View panel)
            {
                slidingLeftFragment.setHasOptionsMenu(true);
            }
        });
    }

    @Override
    protected void initData(Bundle var)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sliding_pane_layout, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onLeftChang(String url)
    {
        slidingRightFragment.setWebViewByUrl(url);
    }
}
