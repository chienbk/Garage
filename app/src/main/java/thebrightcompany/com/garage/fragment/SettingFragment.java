package thebrightcompany.com.garage.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.view.MainActivity;
import thebrightcompany.com.garage.view.SplashActivity;
import thebrightcompany.com.garage.view.login.LoginActivity;

public class SettingFragment extends Fragment {
    public ImageView avartar;
    public TextView txtGarageTitle;
    public TextView txtLocal;
    public TextView txtPhone1, txtPhone2;
    public TextView txtDateCreated;


//    public SettingFragment(View viewParent){
//        this.viewParent = viewParent;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView title = getActivity().findViewById(R.id.txt_header_title);
        title.setText(getResources().getText(R.string.str_setting_title));

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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
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



}

