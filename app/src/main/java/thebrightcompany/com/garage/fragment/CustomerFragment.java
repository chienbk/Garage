package thebrightcompany.com.garage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import butterknife.ButterKnife;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.model.orderonmap.Order;
import thebrightcompany.com.garage.view.MainActivity;

public class CustomerFragment extends Fragment implements CustomerView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    public static final String TAG = CustomerFragment.class.getSimpleName();
    private MainActivity homeActivity;

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
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null){
            //todo update map
        }
    }

    @Override
    public void onNetWorkError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onGetCustomerSuccess(String token, List<Order> orders) {

    }

    @Override
    public void onGetCustomerError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onAddCustomerSuccess(String token, String msg) {

    }

    @Override
    public void onAddCustomerError(String msg) {
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
}

