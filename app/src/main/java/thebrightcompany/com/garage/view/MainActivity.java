package thebrightcompany.com.garage.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.CustomerFragment;
import thebrightcompany.com.garage.fragment.GarageFragment;
import thebrightcompany.com.garage.fragment.NoteFragment;
import thebrightcompany.com.garage.fragment.SettingFragment;
import thebrightcompany.com.garage.service.GPSTracker;
import thebrightcompany.com.garage.utils.AlertDialogUtils;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity implements BaseView{

    public static final String TAG = MainActivity.class.getSimpleName();

    public LinearLayout lnrCustomer;
    public LinearLayout lnrGarage;
    public LinearLayout lnrNote;
    public LinearLayout lnrSetting;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Dialog dlGPS;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        lnrCustomer = (LinearLayout) findViewById(R.id.customer_lnr);
        lnrCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new CustomerFragment());
                updateTabbar(0);

            }
        });

        lnrGarage = (LinearLayout) findViewById(R.id.garage_lnr);
        lnrGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new GarageFragment());
                updateTabbar(1);

            }
        });

        lnrNote = (LinearLayout) findViewById(R.id.note_lnr);
        lnrNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new NoteFragment());
                updateTabbar(2);

            }
        });

        lnrSetting = (LinearLayout) findViewById(R.id.setting_lnr);
        lnrSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingFragment fragment = new SettingFragment();
//                fragment.viewParent = get
                addFragment(new SettingFragment());
                updateTabbar(3);

            }
        });

        addFragment(new CustomerFragment());
        updateTabbar(0);
        startService(new Intent(this, GPSTracker.class));
        sharedPreferencesUtils = new SharedPreferencesUtils(this);

       }

       public void updateTabbar(int tapSelect){
           lnrCustomer.setAlpha(0.5f);
           lnrGarage.setAlpha(0.5f);
           lnrNote.setAlpha(0.5f);
           lnrSetting.setAlpha(0.5f);
           switch (tapSelect){
               case 0:
                   lnrCustomer.setAlpha(1f);
                   break;
               case 1:
                   lnrGarage.setAlpha(1f);
                   break;
               case 2:
                   lnrNote.setAlpha(1f);
                   break;
               default:
                   lnrSetting.setAlpha(1f);
                   break;
           }
       }


    public void addFragment(Fragment fragment) {
        if (fragment != null) {
            hideProgress();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.layoutchange, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


//    public String encodeFileToBase64Binary(ImageView imageView) {
//        Bitmap bmp =((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream bos = null;
//        byte[] bt = null;
//        String encodeString = null;
////        try {
//            bos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 40, bos);
//            bt = bos.toByteArray();
//            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return encodeString;
//    }
//
//    public Bitmap decodeBase64AndSetImage(String completeImageData) {
//
//        // Incase you're storing into aws or other places where we have extension stored in the starting.
//        String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1);
//
//        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
//
//        Bitmap bitmap = BitmapFactory.decodeStream(stream);
//
//        return bitmap;
//    }


    public void setTittle(String tittle){
        setTitle(tittle);
    }

    public boolean checkGPS() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return false;
        }

        return true;
    }

    public void showDialogAskEnableGPS(){
        if (!checkGPS() && (dlGPS == null || !dlGPS.isShowing())) {
            dlGPS = AlertDialogUtils.ShowDialog(this, getString(R.string.dialog_notice),
                    getString(R.string.gps_network_not_enabled), getString(R.string.open_location_settings), true,
                    getString(R.string.close_location_settings), new AlertDialogUtils.IOnDialogClickListener() {

                        @Override
                        public void onClickOk() {
                            // TODO Auto-generated method stub
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }

                        @Override
                        public void onClickCancel() {
                            // TODO Auto-generated method stub
                            dlGPS.cancel();
                        }
                    });
        }
    }

    /**
     * The method
     */
    private BroadcastReceiver broadcastReceiverLatLon = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            updateGPSDone();
        }
    };

    protected void updateGPSDone() {
        // TODO Auto-generated method stub
        Log.d(TAG, "updateGPSDone");
        if (dlGPS != null) {

            dlGPS.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLatLon,
                new IntentFilter(Constant.GPS_FILTER));

        startService(new Intent(this, GPSTracker.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverLatLon);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverLatLon);
        super.onPause();
    }

    /**
     * The method use to update token device
     *
     * @param token is token of device
     */
    public void updateToken(String token){
        //todo something
        if (sharedPreferencesUtils != null){
            sharedPreferencesUtils.writeStringPreference(Constant.PREF_DEVICE_TOKEN, token);
        }else {
            sharedPreferencesUtils = new SharedPreferencesUtils(this);
            sharedPreferencesUtils.writeStringPreference(Constant.PREF_DEVICE_TOKEN, token);
        }
    }

}
