package com.example.administrator.fragment.SlidingPaneLayout;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.fragment.BaseFragment;
import com.example.administrator.testandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlidingRightFragment extends BaseFragment
{
    private WebView webView;

    public SlidingRightFragment()
    {
        // Required empty public constructor
    }

    @Override
    protected void findViews(View v)
    {
        webView = (WebView)v.findViewById(R.id.webView);
        setWebViewByUrl("http://www.baidu.com");
    }

    public void setWebViewByUrl(String url)
    {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.loadUrl(url);
    }

    @Override
    protected void initViews(Bundle var)
    {

    }

    @Override
    protected void initData(Bundle var)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sliding_pane_right, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }
}
