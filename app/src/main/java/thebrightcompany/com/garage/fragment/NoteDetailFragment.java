package thebrightcompany.com.garage.fragment;

import android.Manifest;
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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.api.changestateorder.ChangeStateCallAPI;
import thebrightcompany.com.garage.fragment.note.NoteListAdapter;
import thebrightcompany.com.garage.fragment.note.TroubleAdapter;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;

public class NoteDetailFragment extends Fragment {

    public String noteId;
    public OrderModel orderModel;

    public TextView txtCustomerName;
    public TextView txtCustomerTel;
    public TextView txtAddress;
    public TextView code;
    public TextView carType;

    public TextView txtNumberTrouble;
    public ListView listView;

    public LinearLayout lnrCall;
    public LinearLayout lnrOk;
    public LinearLayout lnrNot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice_customer_detail, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getContext()).setTittle(getResources().getString(R.string.str_note_title));

        txtCustomerName = (TextView)getView().findViewById(R.id.txt_notice_customer_name);
        txtCustomerTel = (TextView)getView().findViewById(R.id.txt_notice_customer_tel);
        txtAddress = (TextView)getView().findViewById(R.id.txt_notice_local);
        code = (TextView)getView().findViewById(R.id.txt_notice_car_number);
        carType = (TextView)getView().findViewById(R.id.txt_notice_car_type);

        lnrCall = (LinearLayout)getView().findViewById(R.id.lnr_notification_call);
        lnrOk = (LinearLayout)getView().findViewById(R.id.lnr_notification_accept);
        lnrNot = (LinearLayout)getView().findViewById(R.id.lnr_notification_delete);


        lnrCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processCallCustomer(orderModel.phone);
            }
        });

        lnrOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            changeState(1);
            }
        });

        lnrNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            changeState(-1);
            }
        });


        txtNumberTrouble = (TextView)getView().findViewById(R.id.txt_number_trouble);
        listView = (ListView)getView().findViewById(R.id.lst_trouble);

        loadNoteDetail();
    }

    public void loadNoteDetail(){
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

                    orderModel = LoganSquare.parse(object.optString("order"),OrderModel.class);
                    String[] info = orderModel.customer_info.split("/n");
                    txtCustomerName.setText(info[0]);
                    txtCustomerTel.setText(orderModel.phone);
                    if(info.length>2)carType.setText(info[2]);
                    code.setText(orderModel.code);
                    txtAddress.setText(orderModel.address);

                    txtNumberTrouble.setText("("+orderModel.getNumberTrouble()+")");

                    TroubleAdapter adapter = new TroubleAdapter(getContext(),R.layout.item_trouble, orderModel.trouble_code);
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String, String> mParams = new HashMap<>();
        mParams.put("token",Utils.APP_TOKEN);

        BaseGetRequest request = new BaseGetRequest( Constant.URL_ORDER_DETAIL + noteId, new TypeToken<JsonObject>(){}.getType(),listener, mParams);
        App.addRequest(request, "orderDetail");

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

    public void changeState(final int status){

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

                    if (status == 1){
                        ((MainActivity)getActivity()).showMessage("Bạn đã chọn sửa chữa cho oto thành công");
                        getFragmentManager().popBackStack();
//                        homeActivity.updateToken(token);
//                        btn_repair.setEnabled(false);
//                        btn_repair.setBackgroundColor(homeActivity.getColor(R.color.color_disable_edit_text));
                    }

                    if (status == -1){
                        ((MainActivity) getActivity()).showMessage("Bạn đã hủy sửa chữa cho oto này");
                        getFragmentManager().popBackStack();
//                        homeActivity.updateToken(token);
//                        btn_cancel.setEnabled(false);
//                        btn_cancel.setBackgroundColor(homeActivity.getColor(R.color.color_disable_edit_text));
                    }

                }
            };

            BasePostRequest request = new BasePostRequest( String.format(Constant.URL_UPDATE_STATE, orderModel.id),
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("state", String.valueOf(status));
            request.setParam("token", Utils.APP_TOKEN);
            App.addRequest(request, "orderDetail");

        }
    }


}
