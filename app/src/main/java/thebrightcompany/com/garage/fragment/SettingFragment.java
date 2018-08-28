package thebrightcompany.com.garage.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.api.login.LoginCallAPI;
import thebrightcompany.com.garage.api.logout.LogoutCallAPI;
import thebrightcompany.com.garage.fragment.note.TroubleAdapter;
import thebrightcompany.com.garage.fragment.setting.GarageModel;
import thebrightcompany.com.garage.model.login.LoginResponse;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;
import thebrightcompany.com.garage.view.login.LoginActivity;

public class SettingFragment extends Fragment {
    public ImageView avartar;
    public TextView txtGarageTitle;
    public TextView txtLocal;
    public TextView txtPhone1;
    public TextView txtEmail;
    public TextView txtDescription;

    private MainActivity homeActivity;


//    public SettingFragment(View viewParent){
//        this.viewParent = viewParent;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeActivity.setTitle(R.string.str_setting_title);

        avartar = (ImageView)getView().findViewById(R.id.img_avartar_setting);
        txtGarageTitle = (TextView)getView().findViewById(R.id.txt_garage_name);
        txtLocal = (TextView)getView().findViewById(R.id.txt_local_setting);
        txtPhone1 = (TextView)getView().findViewById(R.id.txt_phone1);
        txtEmail = (TextView)getView().findViewById(R.id.txt_email);
        txtDescription = (TextView)getView().findViewById(R.id.txt_description);

        LinearLayout lnrLogout = (LinearLayout)getView().findViewById(R.id.lnr_logout);
        lnrLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        loadGarageDetail();
    }

    public void logout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.str_confirm_logout));
        builder.setCancelable(false);
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                processLogout(Utils.APP_TOKEN, Utils.FCM_TOKEN);
            }
        });

        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * The method use to process logout
     * @param appToken
     * @param fcmToken
     */
    private void processLogout(String appToken, String fcmToken) {
        LogoutListener listener = new LogoutListener();
        LogoutCallAPI callAPI = new LogoutCallAPI();
        callAPI.processLogout(appToken, fcmToken, listener);
    }


    private class LogoutListener extends OnResponseListener<LoginResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            redirectToLoginScreen();
        }

        @Override
        public void onResponse(LoginResponse response) {
            super.onResponse(response);
            redirectToLoginScreen();
        }
    }

    /**
     * The method use to redirect to login screen
     */
    private void redirectToLoginScreen(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        homeActivity.updateToken("");
        homeActivity.finish();
    }

    public void loadGarageDetail(){
        if (!Utils.isNetworkAvailable(getContext())){
//            onNetWorkError(getString(R.string.str_msg_network_fail));
            homeActivity.showMessage(getString(R.string.str_msg_network_fail));
            return;
        }

        homeActivity.showProgress();
        OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
            @Override
            public void onErrorResponse(VolleyError error) {
                homeActivity.showMessage("Đã có lỗi xảy ra!");
                homeActivity.hideProgress();
                super.onErrorResponse(error);
            }
            @Override
            public void onResponse(JsonObject response) {
                homeActivity.hideProgress();
                super.onResponse(response);
                try {
                    JSONObject object = new JSONObject(response.get("data").toString());

                    GarageModel garageModel = LoganSquare.parse(object.optString("garage"),GarageModel.class);

                    if(garageModel != null) {
                        txtGarageTitle.setText(garageModel.name);
                        txtLocal.setText(garageModel.address);
                        txtEmail.setText(garageModel.email);
                        txtPhone1.setText(garageModel.getPhoneString());
                        txtDescription.setText(garageModel.description);
                    }
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

        BaseGetRequest request = new BaseGetRequest( Constant.URL_PROFILE, new TypeToken<JsonObject>(){}.getType(),listener, mParams);
        App.addRequest(request, "garageDetail");

    }



}

