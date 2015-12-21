package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.example.administrator.BaseApplication;
import com.example.administrator.activity.BaseActivity;
import com.example.administrator.data.Constants;
import com.example.administrator.testandroiddemo.R;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Administrator on 2015/12/18.
 */
public class GaodeMapFragment extends BaseFragment implements LocationSource, AMapLocationListener,
        OnPoiSearchListener, AMap.InfoWindowAdapter, AMap.OnMarkerClickListener
//下面的implements是android自带的，与高德地图无关
        , RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private final String TAG = GaodeMapFragment.class.getSimpleName();

    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocation mapLocation;
    private UiSettings mapSetting;
    private PoiSearch poiSearch;
    private PoiSearch.Query query;
    private PoiResult poiResult;

    private RadioGroup radioGroup;
    private RadioButton normalRB;
    private RadioButton satelliteRB;
    private RadioButton rotateRB;

    private RadioGroup modeGroup;
    private RadioButton shiliangRB;
    private RadioButton weixingRB;
    private RadioButton nightRB;

    private CheckBox checkBox;
    private RelativeLayout poiLayout;
    private EditText keywordET;
    private EditText cityET;
    private Button searchBtn;
    private Button nextPageBtn;

    private int currentPage = 0;

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

        checkBox =  (CheckBox)v.findViewById(R.id.poiCheck);
        poiLayout = (RelativeLayout)v.findViewById(R.id.poiLayout);
        keywordET = (EditText)v.findViewById(R.id.keywordEdit);
        cityET = (EditText)v.findViewById(R.id.cityEdit);
        searchBtn = (Button)v.findViewById(R.id.searchBtn);
        nextPageBtn = (Button)v.findViewById(R.id.nextPage);
    }

    @Override
    protected void initViews(Bundle var)
    {
        mapView.onCreate(var);
        aMap = mapView.getMap();

        radioGroup.setOnCheckedChangeListener(this);
        modeGroup.setOnCheckedChangeListener(this);

        checkBox.setOnCheckedChangeListener(this);
        searchBtn.setOnClickListener(this);
        nextPageBtn.setOnClickListener(this);
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
        aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
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
                    mapLocation = aMapLocation;
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

    @Override
    public void onPoiSearched(PoiResult poiResult, int i)
    {
        this.dismissProgressDialog();// 显示进度框
        if(i == 0)
        {
            if(null != poiResult && null !=poiResult.getQuery())
            {
                //搜索结果
                if(poiResult.getQuery().equals(query))//是否是同一条
                {
                    this.poiResult = poiResult;
                    // 取得搜索到的poitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if(null != poiItems && poiItems.size() > 0)
                    {
                        aMap.clear();//清理之前的map
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    }
                    else if(null != suggestionCities && suggestionCities.size() > 0)
                    {
                        showSuggestCity(suggestionCities);
                    }
                    else
                    {
                        BaseApplication.application.showToast(R.string.no_result);
                    }
                }
                else
                {
                    BaseApplication.application.showToast(R.string.no_result);
                }

            }
        }
        else if(i == 27)
        {
            BaseApplication.application.showToast(R.string.network_error);
        }
        else if(i == 32)
        {
            BaseApplication.application.showToast(R.string.error_key);
        }
        else
        {
            BaseApplication.application.showToast(R.string.error_other, ":error code = " + i);
        }
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities)
    {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        BaseApplication.application.showToast(infomation);

    }

    private void doSearchPoi()
    {
        this.showProgressDialog(R.string.loading);// 显示进度框
        if(cityET.getText().toString().equals("") || keywordET.getText().toString().equals(""))
        {
            this.dismissProgressDialog();
            BaseApplication.application.showToast(R.string.edit_null);
            return;
        }
        currentPage = 0;
        //第一个参数表示搜索字符串， 第二个参数表示poi搜索类型， 第三个参数表示poi的区域（空字符串代表全国）
        query = new PoiSearch.Query(keywordET.getText().toString(), "", cityET.getText().toString());
        query.setPageNum(currentPage);//查看第一页
        query.setPageSize(Constants.POI_SEARCH_NUM);//每页返回的poitem条数
        poiSearch = new PoiSearch(this.getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);

//        poiSearch.setBound(new SearchBound(lp, 2000, true));//
//        // 设置搜索区域为以lp点为圆心，其周围2000米范围
//        例如LatLonPoint lp = new LatLonPoint(39.908127, 116.375257);// 默认西单广场

        poiSearch.searchPOIAsyn();//异步请求，屏蔽上方方法是按要求所搜所有
    }

    private void netxPage()
    {
        if (query != null && poiSearch != null && poiResult != null)
        {
            if (poiResult.getPageCount() - 1 > currentPage)
            {
                currentPage++;
                query.setPageNum(currentPage);// 设置查后一页
                poiSearch.searchPOIAsyn();
            }
            else
            {
                BaseApplication.application.showToast(R.string.no_result);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == searchBtn)
        {
            Log.v(TAG, "searchBtn");
            doSearchPoi();

        }
        else if(v == nextPageBtn)
        {
            Log.v(TAG, "nextPageBtn");
            netxPage();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        Log.v(TAG, "onCheckedChanged");
        if(isChecked)
        {
            if(poiLayout.getVisibility() == View.INVISIBLE)
            {
                poiLayout.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if(poiLayout.getVisibility() == View.VISIBLE)
            {
                poiLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    //AMap.InfoWindowAdapter中需要实现的2个方法，实现以后可以点击poi(Marker)显示需要显示的展示view
    @Override
    public View getInfoWindow(final Marker marker)
    {
        View view = ((BaseActivity)this.getActivity()).getLayoutInflater().inflate(R.layout.fragment_map_marker_winpop, null);
        TextView titleText = (TextView)view.findViewById(R.id.titleText);
        TextView snippetText = (TextView)view.findViewById(R.id.snippetText);
        Button gaodeBtn = (Button)view.findViewById(R.id.jumpToGaodeBtn);

        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.check);

        titleText.setText(marker.getTitle());
        snippetText.setText(marker.getSnippet());



        gaodeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.v(TAG, "getInfoWindow gaodeBtn");
                if(checkBox.isChecked())
                 startAMapGaode(marker);
                else
                {
                    jumpToWithOutGaodeSdk(marker);
                }
            }
        });

        return view;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        Log.v(TAG, "onMarkerClick");
        marker.showInfoWindow();
        return false;
    }

        /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    private void startAMapGaode(Marker marker)
    {
        //构造导航参数
        NaviPara naviPara = new NaviPara();
        //设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        //设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

        try
        {
            AMapUtils.openAMapNavi(naviPara, getActivity().getApplicationContext());
        }
        catch(com.amap.api.maps.AMapException e)
        {
            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getActivity().getApplicationContext());
        }
    }

    private void jumpToWithOutGaodeSdk(Marker marker)
    {
        if(!getAppIn())
        {
            Uri uri = Uri.parse("http://wap.amap.com/");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);

            return;
        }

        //不使用高德sdk跳转到高德应用
        String uriString = String.format
                (
                "androidamap://navi?sourceApplication=%s&poiname=%s&lat=%s&lon=%s&dev=1&style=2",
                "口袋停", marker.getPosition().describeContents(),
                        marker.getPosition().latitude,
                        marker.getPosition().longitude
        );
        Log.v(TAG, "marker.getPosition().describeContents() = " + marker.getPosition().describeContents());
        try
        {
            Intent intent = Intent.parseUri(uriString,0);
            intent.setPackage("com.autonavi.minimap");
            getActivity().startActivity(intent);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 判断高德地图app是否已经安装
     */
    public boolean getAppIn()
    {
        PackageInfo packageInfo = null;
        try
        {
            packageInfo = getActivity().getPackageManager().getPackageInfo(
                    "com.autonavi.minimap", 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            packageInfo = null;
            e.printStackTrace();
        }
        // 本手机成功安装有高德地图app
        if (packageInfo != null)
        {
            return true;
        }
        // 本手机没有安装高德地图app
        else
        {
            return false;
        }
    }


}
