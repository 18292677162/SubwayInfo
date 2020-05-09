package com.example.subwayinfo.ui.gallery;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.subwayinfo.R;

import java.util.List;


public class GalleryFragment extends Fragment {

    private MapView _mapView = null;

    private AMap _amap = null;

    private MyLocationStyle myLocationStyle = null;

    private int isAddSelfMarker = 0;

    private String end = "(地铁站)";

    private String _city = null; // 当前定位城市

    private LatLonPoint _user_Point = null;

    private Button bt_search = null;

    // 定位服务客户端句柄
    private AMapLocationClient _amapLocationClient = null;
    // 定位句柄属性
    private AMapLocationClientOption _amapLocationOption = null;

    protected void createMap(Bundle savedInstanceState){
        // 展示地图容器
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，创建地图
        _mapView.onCreate(savedInstanceState);

        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.radiusFillColor(0);
        myLocationStyle.strokeColor(0);

        // 操控地图,获取地图对象
        _amap = _mapView.getMap();
        // 交通状况
        _amap.setTrafficEnabled(true);
        // 指南针
        _amap.getUiSettings().setCompassEnabled(true);
        // 缩放按钮
        _amap.getUiSettings().setZoomControlsEnabled(false);
        // 比例尺
        _amap.getUiSettings().setScaleControlsEnabled(true);
        //_amap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        _amap.getUiSettings().setMyLocationButtonEnabled(true); //显示默认的定位按钮


    }

    //以某个经纬度为中心来展示地图
    protected void moveMap(double latitude, double longtiude)
    {
        LatLng lagLng = new LatLng(latitude, longtiude);

        //移动amap地图 以之前的缩放比例展示
        _amap.animateCamera(CameraUpdateFactory.newLatLngZoom(lagLng, 16));
    }

    // 标记接口
    protected void addMarkerToMap(double latitude, double longitude){
        Marker _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.poi_marker_pressed))
                ));
    }

    // 启动定位器
    protected void doLocation() {
        // 创建客户端定位句柄
        _amapLocationClient = new AMapLocationClient(getContext());
        // 设置属性
        _amapLocationOption = new AMapLocationClientOption();

        // 10秒定位一次
        _amapLocationOption.setInterval(2000);

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
                        Log.e("Amap", "gallery!!!");
                        Log.e("Amap", "location success address = "+ aMapLocation.getAddress());
                        Log.e("Amap", "city = "+ aMapLocation.getCity());
                        Log.e("Amap", "longtitude = "+ aMapLocation.getLongitude());
                        Log.e("Amap", "latitude = "+ aMapLocation.getLatitude());

                        _city = aMapLocation.getCity();
                        _user_Point = new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());


                        if(isAddSelfMarker <= 2){
                            // Todo:
                            // addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            isAddSelfMarker++;

                            // 以自我我中心展示地图
                            moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        }
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


    // POI兴趣点搜素
    protected void doSearchPOI(){

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _amap.clear();

                // 开始POI搜索
                // 创建搜索条件对象 query

                PoiSearch.Query query = new PoiSearch.Query(end, "", _city);
                // 构造Poisearch 对象监听
                PoiSearch poiSearch = new PoiSearch(getContext(), query);
                // 回调

                poiSearch.setBound(new PoiSearch.SearchBound(_user_Point, 2000));//设置周边搜索的中心点以及半径
                poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        // 返回POI集合
                        if(i != 1000){
                            Log.e("Amap", "poi Search error code = " + i);
                            return;
                        }

                        // 成功
                        List<PoiItem> poiList = poiResult.getPois();
                        for (int index = 0; index < poiList.size(); index++){
                            // 获得兴趣点元素信息
                            PoiItem item = poiList.get(index);
                            Log.e("Amap", "搜索的兴趣点");
                            Log.e("Amap", "POI title = " + item.getTitle() + "latitude = " +
                                    item.getLatLonPoint().getLatitude() + "longitude = " + item.getLatLonPoint().getLongitude());

                            // 画一个标记
                            addMarkerToMap(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());


                        }

                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                // 发送请求
                poiSearch.searchPOIAsyn();
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // 关联地图空间
        _mapView = view.findViewById(R.id.gallery_map);
        bt_search = view.findViewById(R.id.bt_serch);

        createMap(savedInstanceState);

        // 小蓝点定位
        _amap.animateCamera(CameraUpdateFactory.zoomBy(16));
        _amap.setMyLocationStyle(myLocationStyle);
        _amap.setMyLocationEnabled(true);

        doLocation();

        doSearchPOI();

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        // _mapView.onDestroy();
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

