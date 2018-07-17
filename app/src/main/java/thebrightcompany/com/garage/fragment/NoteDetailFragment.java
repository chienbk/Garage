package thebrightcompany.com.garage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ListView lstView;
    public NoteListAdapter adapter;
    public List<NoteModel> notes;

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

        txtNumberTrouble = (TextView)getView().findViewById(R.id.txt_number_trouble);
        listView = (ListView)getView().findViewById(R.id.lst_trouble);
//        txtCustomerName = (TextView)getView().findViewById(R.id.txt_notice_customer_name);

//        lstView = (ListView) getView().findViewById(R.id.lst_note);
//        notes = new ArrayList<>();
//        adapter = new NoteListAdapter(getContext(), R.layout.item_notice_customer );
//
//        adapter.notes = this.notes;
//        lstView.setAdapter(adapter);
//
//        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // go to detail
//            }
//        });

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
//                    notes = LoganSquare.parseList(object.optString("notifications"),NoteModel.class);
//                    adapter.notes = notes;
//                    adapter.notifyDataSetChanged();

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



}
