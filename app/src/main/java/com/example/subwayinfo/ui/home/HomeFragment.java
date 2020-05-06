package com.example.subwayinfo.ui.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.subwayinfo.R;
import com.example.subwayinfo.overlay.BusRouteOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToDoubleFunction;


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

    private Button _bt_route = null;
    private AutoCompleteTextView _atv_start = null;
    private AutoCompleteTextView _atv_end = null;

    private String _city = null; // 当前定位城市

    // 起点终点
    private LatLonPoint _startPoint = null;
    private LatLonPoint _endPoint = null;

    private MarkerOptions markopt = null;

    protected void createMap(Bundle savedInstanceState){
        // 展示地图容器
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，创建地图
        _mapView.onCreate(savedInstanceState);


        // Todo
        myLocationStyle = new MyLocationStyle();
        // 只定位一次
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式
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
        _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
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
        _amapLocationOption.setInterval(3000);

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

                        if(isAddSelfMarker == false){
                            // Todo:
                            // addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            isAddSelfMarker = true;

                            // 以自我我中心展示地图
                            moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        }

                        // _amap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));

                        // 当前为起始位置
                        if(_startPoint == null){
                            _startPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        }

                        //设置用户地址信息
                        _atv_start.setText(aMapLocation.getAddress());

                        _city = aMapLocation.getCity();

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


    // 绘制路线
    protected void drawPoute()
    {
        // 创建路径句柄
        RouteSearch routeSearch = new RouteSearch(getContext());
        RouteSearch.FromAndTo fat = new RouteSearch.FromAndTo(_startPoint, _endPoint);

        // 设置搜索 query
        RouteSearch.BusRouteQuery query= new RouteSearch.BusRouteQuery(fat, RouteSearch.BUS_LEASE_WALK, _city, 1);
        // 设置callback函数
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                if(i != 1000){
                    Log.e("Amap", "搜索出行路径失败");
                    return;
                }
                // 画出路径
                BusPath bPath = busRouteResult.getPaths().get(0);

                // 公交路径覆盖物

                BusRouteOverlay busRouteOverlay = new BusRouteOverlay(
                        getContext(), _amap, bPath, _startPoint, _endPoint
                );

                // 删除之前路径
                // ToDo:
                // _amap.clear();

                // 缩放路径
                busRouteOverlay.zoomToSpan();

                // 转弯图标提示
                // busRouteOverlay.setNodeIconVisibility(false);

                // 添加地图
                busRouteOverlay.addToMap();
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        // 开始搜索
        routeSearch.calculateBusRouteAsyn(query);
    }


    // POI兴趣点搜素
    protected void doSearchPOI(){
        _bt_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                _amap.clear();


                // 搜索POI兴趣点
                Log.e("Amap", "button onclick");
                // 拿到搜索关键字
                String start = _atv_start.getText().toString();
                String end = _atv_end.getText().toString();
                // 开始POI搜索
                // 创建搜索条件对象 query

                PoiSearch.Query query = new PoiSearch.Query(end, "", _city);
                // 构造Poisearch 对象监听
                PoiSearch poiSearch = new PoiSearch(getContext(), query);
                // 回调
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

                            _endPoint = new LatLonPoint(item.getLatLonPoint().getLatitude(),
                                    item.getLatLonPoint().getLongitude());

                            // 默认选取第一个点
                            if(index == 0){
                                drawPoute();
                            }


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


    // 自动弹出POI列表
    protected void initAutoCompeleteTextViewEnd(){
        // 设置阈值（开始匹配的个数）
        _atv_end.setThreshold(1);

        // 绑定自动补全功能
        _atv_end.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // 获得匹配数据

                // 1.得到搜索的关键字
                final String keyword = _atv_end.getText().toString();
                if(keyword == null || keyword.length() == 0){
                    Log.e("Amap", "search keyword == null");
                    return;
                }
                // 2.创建 query 查询所有 tips
                InputtipsQuery query = new InputtipsQuery(keyword, _city);
                // 3.创建 InputTips 查询语句
                Inputtips search = new Inputtips(getContext(), query);
                // 4.设定回调
                search.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> list, int i) {
                        if(i != 1000){
                            Log.e("Amap", "search input tips error i = " + i);
                            return;
                        }


                        // 获得匹配的单词集合
                        ArrayList<String> poiList = new ArrayList<String>();


                        for (int index = 0; index < list.size(); index++){
                            Log.e("Amap", "通过"+keyword+"匹配到的tips :"+list.get(index).getName());
                            poiList.add(list.get(index).getName());
                        }

                        // 设置适配器 Adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_expandable_list_item_1, poiList);
                        // 关联适配器和控件
                        _atv_end.setAdapter(adapter);
                        // 触发控件显示
                        adapter.notifyDataSetChanged();
                    }
                });
                // 5.开始查询
                search.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        _bt_route = view.findViewById(R.id.bt_route);
        _atv_start = view.findViewById(R.id.atv_start);
        _atv_end = view.findViewById(R.id.atv_end);

        // 关联地图空间
        _mapView = view.findViewById(R.id.home_map);

        createMap(savedInstanceState);

        // 小蓝点定位
        _amap.animateCamera(CameraUpdateFactory.zoomBy(16));
        _amap.setMyLocationStyle(myLocationStyle);
        _amap.setMyLocationEnabled(true);


        doLocation();

        doSearchPOI();
        initAutoCompeleteTextViewEnd();

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

