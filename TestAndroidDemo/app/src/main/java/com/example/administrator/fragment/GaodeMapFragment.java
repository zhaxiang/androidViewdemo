package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.administrator.testandroiddemo.R;

/**
 * Created by Administrator on 2015/12/18.
 */
public class GaodeMapFragment extends BaseFragment implements LocationSource,
        AMapLocationListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener
{
    private final String TAG = GaodeMapFragment.class.getSimpleName();

    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private UiSettings mapSetting;

    private RadioGroup radioGroup;
    private RadioButton normalRB;
    private RadioButton satelliteRB;
    private RadioButton rotateRB;

    private RadioGroup modeGroup;
    private RadioButton shiliangRB;
    private RadioButton weixingRB;
    private RadioButton nightRB;

    private Button signBtn;



    @Override
    protected void findViews(View v)
    {
        mapView = (MapView)v.findViewById(R.id.mapView);
        radioGroup = (RadioGroup)v.findViewById(R.id.dingweiType);
        normalRB = (RadioButton)v.findViewById(R.id.normalRB);
        satelliteRB = (RadioButton)v.findViewById(R.id.satelliteRB);
        rotateRB = (RadioButton)v.findViewById(R.id.rotateRB);

        modeGroup = (RadioGroup)v.findViewById(R.id.mapType);
        shiliangRB = (RadioButton)v.findViewById(R.id.shilianglRB);
        weixingRB = (RadioButton)v.findViewById(R.id.weixingRB);
        nightRB = (RadioButton)v.findViewById(R.id.nightRB);

        signBtn =  (Button)v.findViewById(R.id.signBtn);
        signBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle var)
    {
        mapView.onCreate(var);
        aMap = mapView.getMap();

        radioGroup.setOnCheckedChangeListener(this);
        modeGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData(Bundle var)
    {
        setMapLocation();

    }

    //设置地图定位
    private void setMapLocation()
    {
        aMap.setLocationSource(this);//设置定位监听回调
        mapSetting = aMap.getUiSettings();
        mapSetting.setMyLocationButtonEnabled(true);// 设置默认定位按钮(右上角)是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mapSetting();
    }

    //地图 设置相关功能
    private void mapSetting()
    {
        if(null != mapSetting)
        {
            //自定义系统定位蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            //自定义定位蓝点图标
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
            //自定义精确范围圈的边框颜色
            myLocationStyle.strokeColor(Color.RED);
            //自定义圈的边框宽度
            myLocationStyle.strokeWidth(10);
            //设置高地地图四个字的位置 可以左下角 右下角 和底部居中
            mapSetting.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
            //设置高德地图的指南针显示
            mapSetting.setCompassEnabled(true);
            //设置高德地图比例尺是否显示，在左下角
            mapSetting.setScaleControlsEnabled(true);
            //设置地图缩放界别  4-20级 越高看的越详细
//            aMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//不带动画的
            //讲自定义的myLocationStyle对象添加到地图
            aMap.setMyLocationStyle(myLocationStyle);

        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == signBtn)
        {
            Log.v(TAG, "signBtn");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        if(group == radioGroup)
        {
            if(checkedId == normalRB.getId())
            {
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                Log.v(TAG, "LOCATION_TYPE_LOCATE");
            }
            else if(checkedId == satelliteRB.getId())
            {
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                Log.v(TAG, "LOCATION_TYPE_MAP_FOLLOW");
            }
            else
            {
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                Log.v(TAG, "LOCATION_TYPE_MAP_ROTATE");
            }
        }
        else if(group == modeGroup)
        {
            if(checkedId == shiliangRB.getId())
            {
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            }
            else if(checkedId == weixingRB.getId())
            {
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            }
            else
            {
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
            }
        }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_map, null);

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(null != mapView)
        {
            mapView.onDestroy();
            if(null != mlocationClient)
            {
                mlocationClient.onDestroy();
            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(null != mapView)
            mapView.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(null != mapView)
        {
            mapView.onPause();
            deactivate();
        }

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(null != mapView)
            mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation)
    {
        Log.v(TAG, "onLocationChanged");
        if(mListener != null)
        {
            if(aMapLocation != null)
            {
                if(aMapLocation.getErrorCode() == 0)
                {
                    mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                }
                else
                {
                    String errText = R.string.fail_dingwei + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                    Toast.makeText(this.getActivity().getApplicationContext(), errText, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    //LocationSource 需要实现的方法
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener)
    {
        Log.v(TAG, "activate");
        mListener = onLocationChangedListener;
        if(mlocationClient == null)
        {
            mlocationClient = new AMapLocationClient(this.getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate()
    {
        Log.v(TAG, "deactivate");
        mListener = null;
        if (mlocationClient != null)
        {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
