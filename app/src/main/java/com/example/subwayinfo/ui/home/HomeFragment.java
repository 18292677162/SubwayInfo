package com.example.subwayinfo.ui.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.subwayinfo.R;


public class HomeFragment extends Fragment {


    private UiSettings _uiSettings = null;

    private MapView _mapView = null;

    private AMap _amap = null;

    // 定位服务客户端句柄
    private AMapLocationClient _amapLocationClient = null;
    // 定位句柄属性
    private AMapLocationClientOption _amapLocationOption = null;

    private Marker _selfMarker = null;

    private MyLocationStyle myLocationStyle = null;

    private boolean isAddSelfMarker = false;


    protected void createMap(Bundle savedInstanceState){
        // 展示地图容器
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，创建地图
        _mapView.onCreate(savedInstanceState);

        myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.radiusFillColor(0);
        myLocationStyle.strokeColor(0);


        // 操控地图,获取地图对象
        _amap = _mapView.getMap();
        // 交通状况
        _amap.setTrafficEnabled(true);
        // 得到 UI settings
        _uiSettings = _amap.getUiSettings();
        // 指南针
        _uiSettings.setCompassEnabled(true);
        // 缩放按钮
        _uiSettings.setZoomControlsEnabled(false);
        // 比例尺
        _uiSettings.setScaleControlsEnabled(true);
        //_amap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        _uiSettings.setMyLocationButtonEnabled(false); //显示默认的定位按钮

        _amap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(108.95, 34.27), 16));
    }

    //以某个经纬度为中心来展示地图
    protected void moveMap(double latitude, double longtiude)
    {
        LatLng lagLng = new LatLng(latitude, longtiude);

        //移动amap地图 以之前的缩放比例展示
        _amap.animateCamera(CameraUpdateFactory.newLatLngZoom(lagLng, _amap.getCameraPosition().zoom));
    }

    // 标记接口
    protected void addMarkerToMap(double latitude, double longitude){
        _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))
                ));
    }

    // 启动定位器
    protected void doLocation() {
        // 创建客户端定位句柄
        _amapLocationClient = new AMapLocationClient(getContext());
        // 设置属性
        _amapLocationOption = new AMapLocationClientOption();

        // 2秒定位一次
        // _amapLocationOption.setInterval(2000);

        // 将 option 设置给 client 对象
        _amapLocationClient.setLocationOption(_amapLocationOption);

        // 设置listenner 获取数据
        _amapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                // 服务器返回数据时的回调函数
                if(null != aMapLocation){
                    // 有相应
                    if(aMapLocation.getErrorCode() == 0){
                        // 定位成功
                        Log.e("Amap", "location success address = "+ aMapLocation.getAddress());
                        Log.e("Amap", "city = "+ aMapLocation.getCity());
                        Log.e("Amap", "longtitude = "+ aMapLocation.getLongitude());
                        Log.e("Amap", "latitude = "+ aMapLocation.getLatitude());

                        // 添加标记
                        /*
                        if(isAddSelfMarker == false){
                            addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            isAddSelfMarker = true;
                        }
                        */
                        _amap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));
                        // moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                    else{
                        // 定位返回值失败
                        Log.e("Amap", "location error, code = "+ aMapLocation.getErrorCode() +
                                ", info = "+ aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        // 开启定位
        _amapLocationClient.startLocation();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        // 关联地图空间
        _mapView = view.findViewById(R.id.home_map);

        createMap(savedInstanceState);

        // 定位
        _amap.setMyLocationStyle(myLocationStyle);
        _amap.setMyLocationEnabled(true);
        // doLocation();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        _mapView.onDestroy();
        if(null != _amapLocationClient)
            _amapLocationClient.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        _mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        _mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        _mapView.onSaveInstanceState(outState);
    }

}

