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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.fragment.garage.GarageListAdapter;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;

public class GarageFragment extends Fragment implements GarageListAdapter.GaraListAdapterDelegate{

    public static int limit = 20;
    public int status = 1;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

//    public Date					dateReminder;
    public OrderModel  orderModelchoseTime;

    public SwitchDateTimeDialogFragment dateTimeFragment;

    List<OrderModel> orderModelList = new ArrayList<>();

    public LinearLayout lnrDangSua;
    public LinearLayout lnrDaXong;
    public LinearLayout lnrThongBao;
    public ListView lstView;

    public GarageListAdapter adapter;

    private MainActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity.setTitle("Lịch sử khách hàng");


    }

    //https://github.com/Kunzisoft/Android-SwitchDateTimePicker/blob/master/sample/src/main/java/com/kunzisoft/switchdatetimesample/Sample.java
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lnrDangSua = (LinearLayout) getView().findViewById(R.id.lnr_bt_dang_sua);
        lnrDangSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(1);
                orderModelList.clear();
                loadListOrder(0);
            }
        });

        lnrDaXong = (LinearLayout) getView().findViewById(R.id.lnr_bt_da_xong);
        lnrDaXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus(2);
                orderModelList.clear();
                loadListOrder(0);
//                showDateTimePicker();
            }
        });

        createDateTimeDialog();
        lnrThongBao = (LinearLayout)getView().findViewById(R.id.lnr_not_data);
        setStatus(1);
        loadListOrder(0);

        lstView = (ListView)getView().findViewById(R.id.lst_garage);
        adapter = new GarageListAdapter(getContext(), R.layout.item_garage_fixing);
        adapter.orderModels = new ArrayList<>();
        adapter.delegate = this;
        lstView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Lịch sử khách hàng");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (MainActivity) context;
    }

    public void loadListOrder(int start){
        if (!Utils.isNetworkAvailable(getContext())){
//            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }

        OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
            @Override
            public void onErrorResponse(VolleyError error) {

                super.onErrorResponse(error);
            }

            @Override
            public void onResponse(JsonObject response) {

                super.onResponse(response);
                try {
                    JSONObject object = new JSONObject(response.get("data").toString());

                   String datakaka = object.opt("orders").toString();
                    List<OrderModel> orderModels = LoganSquare.parseList(datakaka, OrderModel.class);
                    orderModelList.addAll(orderModels);
                    if(orderModelList.size() == 0){
                        lnrThongBao.setVisibility(View.VISIBLE);
                    }else {
                        lnrThongBao.setVisibility(View.GONE);
                    }
                    adapter.orderModels = orderModels;
                    adapter.notifyDataSetChanged();
//                    int size = orderModels.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String, String> mParams = new HashMap<>();
        mParams.put("start", String.valueOf(start));
        mParams.put("limit", String.valueOf(limit));
        mParams.put("status",String.valueOf(status));
        mParams.put("token",Utils.APP_TOKEN);

        BaseGetRequest request = new BaseGetRequest( Constant.URL_ORDER_LIST, new TypeToken<JsonObject>(){}.getType(),listener, mParams);
        App.addRequest(request, Constant.URL_ORDER_LIST);

    }

    public void setStatus(int status){
        this.status = status;
        switch (status){
            case 1:
                lnrDangSua.setAlpha(1);
                lnrDaXong.setAlpha(0.5f);
                break;

            default:
                lnrDaXong.setAlpha(1);
                lnrDangSua.setAlpha(0.5f);
                break;
        }
    }


    public void showDateTimePicker() {
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.setDefaultDateTime(new Date());
        dateTimeFragment.show(getFragmentManager(), "TAG_DATETIME_FRAGMENT");
    }


    public void createDateTimeDialog(){
        dateTimeFragment = (SwitchDateTimeDialogFragment) getFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }

        dateTimeFragment = new SwitchDateTimeDialogFragment();
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
//			Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                updateTime(orderModelchoseTime, myDateFormat.format(date));
//                orderModelchoseTime.time_finish = myDateFormat.format(date);
//                adapter.notifyDataSetChanged();
//                dateReminder = date;
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

        });
    }


    @Override
    public void choseTime(OrderModel order) {
        this.orderModelchoseTime = order;
        showDateTimePicker();
    }

    @Override
    public void completeOrder(OrderModel order) {
        complete(order);
    }

    @Override
    public void callCustomer(String phoneNumer) {
        processCallCustomer(phoneNumer);
    }

    public void processCallCustomer(String phone) {
        if (phone != null && phone.length() > 0) {
            phone = phone.replaceAll("\\s+", "");

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CustomerFragment.REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + phone));
                startActivity(callSupport);
            }
        } else {
            ((MainActivity) getContext()).showMessage("Garage này chưa cập nhật số điện thoại!");
        }
    }

    public void complete(final OrderModel order){

        if (!Utils.isNetworkAvailable(getActivity())){
            return;
        } else {
            OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
                @Override
                public void onErrorResponse(VolleyError error) {

                    super.onErrorResponse(error);
                }

                @Override
                public void onResponse(JsonObject response) {

                    super.onResponse(response);
                     ((MainActivity)getActivity()).showMessage("Bạn đã hoàn thành công việc này");
                    adapter.orderModels.remove(order);
                    adapter.notifyDataSetChanged();
                }
            };

            BasePostRequest request = new BasePostRequest( String.format(Constant.URL_UPDATE_STATE, order.id),
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("state", String.valueOf(Constant.GARAGE_STATE_FIXED));
            request.setParam("token", Utils.APP_TOKEN);
            App.addRequest(request, "CompleteOrder");

        }
    }


    public void updateTime(final OrderModel order, final String time){

        if (!Utils.isNetworkAvailable(getActivity())){
            return;
        } else {
            OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
                @Override
                public void onErrorResponse(VolleyError error) {

                    super.onErrorResponse(error);
                }

                @Override
                public void onResponse(JsonObject response) {

                    super.onResponse(response);
                    ((MainActivity)getActivity()).showMessage("update thành công!");
                    orderModelchoseTime.end_time = time;
                    adapter.notifyDataSetChanged();
                }
            };

            BasePostRequest request = new BasePostRequest( String.format(Constant.URL_UPDATE_TIME, order.id),
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("time_finish", time);
            request.setParam("token", Utils.APP_TOKEN);
            App.addRequest(request, "updatetime");

        }
    }

}

