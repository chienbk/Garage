package thebrightcompany.com.garage.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.orders.GetOrdersRequest;
import thebrightcompany.com.garage.model.LatLongMessage;
import thebrightcompany.com.garage.model.login.Garage;
import thebrightcompany.com.garage.model.orderonmap.Order;
import thebrightcompany.com.garage.model.orderonmap.OrderResponse;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.SharedPreferencesUtils;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;
import thebrightcompany.com.garage.view.addcustomer.CreateCustomerActivity;

public class CustomerFragment extends Fragment implements CustomerView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    public static final String TAG = CustomerFragment.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL = 101;

    private MainActivity homeActivity;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private String idOrder;
    private int idOfGarage;
    private GoogleMap mGoogleMap;
    private List<Order> mOrders = new ArrayList<>();

    //Use to get my location
    private double mLng = -34;
    private double mLat = 151;
    private Marker currentMarker;
    private boolean isChoice = false;
    private boolean isFirstOpen = true;
    private boolean isFirstCallAPI = true;

    @BindView(R.id.layout_detail)
    LinearLayout layout_detail;
    @BindView(R.id.txt_orderId) TextView txt_orderId;
    @BindView(R.id.txt_nameOfCustomer) TextView txt_nameOfCustomer;
    @BindView(R.id.txt_typeOfCar) TextView txt_typeOfCar;
    @BindView(R.id.txt_licenceOfCar) TextView txt_licenceOfCar;
    @BindView(R.id.txt_troubleCode) TextView txt_troubleCode;

    private String phone = "";
    private Order mOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this, view);
        initMaps();
        initView(view);
        return view;
    }

    /**
     * The method use to initView
     * @param view
     */
    private void initView(View view) {
        setHasOptionsMenu(true);
        homeActivity.showDialogAskEnableGPS();
        homeActivity.setTitle("Khách hàng mới");
        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        idOfGarage = sharedPreferencesUtils.readIntegerPreference(Constant.GARAGE_ID, 0);
        mLat = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Constant.PREF_LAT, "0"));
        mLng = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Constant.PREF_LNG, "0"));

        //Use when google maps ready
        if (!Utils.isNetworkAvailable(homeActivity)){
            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }
        //processGetLisCustomer();
    }

    /**
     * The method use to get lis customer on map
     * @param
     */
    private void processGetLisCustomer() {
        showProgress();
        OrdersResponseListener listener = new OrdersResponseListener();
        GetOrdersRequest request = new GetOrdersRequest(listener);
        App.addRequest(request, "Orders");

    }

    private class OrdersResponseListener extends OnResponseListener<OrderResponse>{

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            onGetCustomerError("Đã có lỗi xảy ra, vui lòng thử lại sau.");

        }

        @Override
        public void onResponse(OrderResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                onGetCustomerSuccess(response.getToken(), response.getOrders());
            }else {
                onGetCustomerError(response.getMessage());
            }
        }
    }

    /**
     * The method use to init google maps
     */
    private void initMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (MainActivity) context;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String id = marker.getTag().toString();

        if (Integer.parseInt(id) != -1){
            Order order = new Order();
            for (int i = 0; i < mOrders.size(); i ++){
                if (id == mOrders.get(i).getOrder_id()){
                    //idOfGarage = id;
                    order = mOrders.get(i);
                    idOrder = order.getOrder_id();

                    if (isChoice && idOrder.equals(i)){
                        layout_detail.setVisibility(View.GONE);
                        isChoice = false;
                    }else {
                        processDisplayInformationOfOrder(order);
                    }

                    break;
                }

            }
        }
        return false;
    }

    /**
     * The method use to show order
     *
     * @param order
     */
    private void processDisplayInformationOfOrder(Order order) {
        isChoice = true;
        //idOfGarage =  order.getOrder_id();
        this.mOrder = order;
        layout_detail.setVisibility(View.VISIBLE);
        try {
            phone = order.getPhone();
            txt_orderId.setText(order.getOrder_id());
            txt_nameOfCustomer.setText(order.getNameOfCustomer());
            txt_typeOfCar.setText(order.getTypeOfCar());
            txt_licenceOfCar.setText(order.getLicenseOfCar());
            txt_troubleCode.setText(order.getTrouble_code().toString());
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.clear();
        if (mGoogleMap != null){
            mGoogleMap.setOnMarkerClickListener(this);
            moveCamera(mLat, mLng);
            //Process get orders

        }
    }

    /**
     *
     * @param cr_lat
     * @param cr_lng
     */
    private void moveCamera(double cr_lat, double cr_lng) {

        if (mGoogleMap != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cr_lat, cr_lng), 15));
        }
    }

    @Override
    public void onNetWorkError(String msg) {
        hideProgress();
        showMessage(msg);
    }

    @Override
    public void onGetCustomerSuccess(String token, List<Order> orders) {
        hideProgress();
        this.mOrders = orders;
        addGarageToMap(orders);
    }

    /**
     * The method use to get list data
     * @param orders
     */
    private void addGarageToMap(List<Order> orders) {
        if (orders.size() > 0 && orders != null){
            for (Order gara : orders){
                Marker marker =
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(gara.getLat(), gara.getLng()))
                                //.title(title)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_customer_on_map))
                                .alpha(0.7f));
                marker.setTag(gara.getOrder_id());
            }
        }
    }

    //Handle the data receive from bluetooth
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LatLongMessage event){
        //todo something
        mLat = event.getLat();
        mLng = event.getLng();
        Log.d(TAG, "lat: " + mLat);
        Log.d(TAG, "lng: " + mLng);

        if (mGoogleMap != null && isFirstCallAPI){
            mGoogleMap.clear();
            //presenter.processGetGarageOnMap(Utils.APP_TOKEN, mLat, mLng, 5);
            //todo something
            isFirstCallAPI = false;
        }
        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLng);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            if (isFirstOpen){
                //initRecycleView();
                moveCamera(mLat, mLng);
                isFirstOpen = false;
            }
        }

    }

    @Override
    public void onGetCustomerError(String msg) {
        hideProgress();
        showMessage(msg);
    }

    @Override
    public void onAddCustomerSuccess(String token, String msg) {
        hideProgress();

    }

    @Override
    public void onAddCustomerError(String msg) {
        hideProgress();
        showMessage(msg);
    }

    @Override
    public void showProgress() {
        homeActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        homeActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        homeActivity.showMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Khách hàng mới");
    }

    @OnClick(R.id.fab_add)
    public void focusMyLocation(){
        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLng);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            moveCamera(mLat, mLng);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_add_order, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_order:
                startActivity(new Intent(homeActivity, CreateCustomerActivity.class));
                break;
        }
        return true;

    }

    @OnClick(R.id.btn_call)
    public void processCall(){
        //todo call customer
        processCallCustomer(mOrder.getPhone());
    }

    @OnClick(R.id.btn_repair)
    public void processRepair(){
        //todo accept repair

    }

    @OnClick(R.id.btn_cancel)
    public void processCancelOrder(){
        //todo cancel order

    }

    /**
     * The method use to call garage
     *
     * @param phone
     */
    private void processCallCustomer(String phone) {
        if (phone != null && phone.length() > 0){
            phone = phone.replaceAll("\\s+","");

            if (ContextCompat.checkSelfPermission(homeActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + phone));
                startActivity(callSupport);
            }
        }else {
            showMessage("Garage này chưa cập nhật số điện thoại!");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHONE_CALL){
            if (ContextCompat.checkSelfPermission(homeActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + phone));
                startActivity(callSupport);
            }
        }
    }

    @OnClick(R.id.layout_detail)
    public void processRedirectOrderDetail(){
        //todo process redirect order detail
        /*Intent intent = new Intent();
        intent.putExtra("idOfGarage", mOrder.getOrder_id());
        startActivity(intent);*/
        showMessage("Redirect to order detail!");
    }
}

