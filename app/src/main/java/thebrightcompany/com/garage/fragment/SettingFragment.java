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

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.login.LoginCallAPI;
import thebrightcompany.com.garage.api.logout.LogoutCallAPI;
import thebrightcompany.com.garage.model.login.LoginResponse;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;
import thebrightcompany.com.garage.view.login.LoginActivity;

public class SettingFragment extends Fragment {
    public ImageView avartar;
    public TextView txtGarageTitle;
    public TextView txtLocal;
    public TextView txtPhone1, txtPhone2;
    public TextView txtDateCreated;

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
        txtPhone2 = (TextView)getView().findViewById(R.id.txt_phone2);
        txtDateCreated = (TextView)getView().findViewById(R.id.txt_date_created);

        LinearLayout lnrLogout = (LinearLayout)getView().findViewById(R.id.lnr_logout);
        lnrLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void logout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.str_confirm_logout));
        builder.setCancelable(false);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                processLogout(Utils.APP_TOKEN, Utils.FCM_TOKEN);
            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
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


}

