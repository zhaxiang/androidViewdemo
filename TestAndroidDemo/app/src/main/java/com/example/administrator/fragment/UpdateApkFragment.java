package com.example.administrator.fragment;


import android.app.DownloadManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.download.DownloadApk;
import com.example.administrator.download.DownloadListener;
import com.example.administrator.testandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateApkFragment extends BaseFragment implements View.OnClickListener
{
    private final String TAG = UpdateApkFragment.class.getSimpleName();

    private Button downloadBtn;
    private Button deleteBtn;
    private TextView downloadText;
    private ProgressBar progressBar;

    public UpdateApkFragment()
    {
        // Required empty public constructor
    }


    @Override
    protected void findViews(View v)
    {
        downloadBtn = (Button)v.findViewById(R.id.downloadBtn);
        deleteBtn = (Button)v.findViewById(R.id.deleteBtn);
        downloadText = (TextView)v.findViewById(R.id.downloadText);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
    }

    @Override
    protected void initViews(Bundle var)
    {
        downloadBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle var)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_update_apk, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onClick(View v)
    {
        if(v == downloadBtn)
        {
            Log.v(TAG, "downloadBtn");
            DownloadApk.getInstance(getActivity()).downloadApk("http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk",
                    "test download", "test download bottom", new DownloadListener()
                    {

                        @Override
                        public void start()
                        {
                            downloadText.setText("start download!");
                        }

                        @Override
                        public void progress(long currentSize, long totalSize)
                        {
                            progressBar.setMax((int)totalSize);
                            progressBar.setProgress((int)currentSize);
                        }

                        @Override
                        public void success()
                        {
                            downloadText.setText("success download!");
                        }

                        @Override
                        public void cancel()
                        {
                            downloadText.setText("cancel download!");
                        }

                        @Override
                        public void fail()
                        {

                        }
                    });
        }
        else if(v == deleteBtn)
        {
            Log.v(TAG, "deleteBtn");
            DownloadApk.getInstance(getActivity()).removeDownloadApk();
        }
    }
}
