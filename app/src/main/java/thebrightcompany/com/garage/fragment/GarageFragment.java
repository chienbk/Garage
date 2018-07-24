package thebrightcompany.com.garage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;

public class GarageFragment extends Fragment {

    public static int limit = 20;
    public int status = 1;

    List<OrderModel> orderModelList = new ArrayList<>();

    public LinearLayout lnrDangSua;
    public LinearLayout lnrDaXong;
    public LinearLayout lnrThongBao;
    public ListView lstView;

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
            }
        });

        lnrThongBao = (LinearLayout)getView().findViewById(R.id.lnr_not_data);
        setStatus(1);
        loadListOrder(0);
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
//                    adapter.notes = notes;
//                    adapter.notifyDataSetChanged();
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



}

