package thebrightcompany.com.garage.view.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.login.LoginCallAPI;
import thebrightcompany.com.garage.fcm.app.Config;
import thebrightcompany.com.garage.fcm.utils.NotificationUtils;
import thebrightcompany.com.garage.model.login.Garage;
import thebrightcompany.com.garage.model.login.LoginResponse;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.SharedPreferencesUtils;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE_LOC = 22;

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.email)
    EditText txt_email;
    @BindView(R.id.password)
    EditText txt_password;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initGoogleFirebase();
        initView();

        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        String deviceToken = sharedPreferencesUtils.readStringPreference(Constant.PREF_DEVICE_TOKEN, "");
        if (!TextUtils.isEmpty(deviceToken)){
            Utils.APP_TOKEN = deviceToken;
            Log.d(TAG, "device token: " + Utils.APP_TOKEN);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    /**
     * Init google firebase
     */
    private void initGoogleFirebase() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Log.d(TAG, "message: " + message);
                }
            }
        };
        displayFirebaseRegId();
    }

    /**
     * Display firebase register
     */
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            Utils.FCM_TOKEN = regId;
            Log.d("Firebase Reg Id: ", " - " + regId);
        } else
            Log.d(TAG, "Firebase Reg Id is not received yet!");
    }

    /**
     * Method use to init view
     */
    private void initView() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accessLocationPermission();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation   = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        int accessWriteToExternal = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int accessCall = checkSelfPermission(Manifest.permission.CALL_PHONE);
        int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);

        List<String> listRequestPermission = new ArrayList<String>();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (accessWriteToExternal != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (accessCall != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.CALL_PHONE);
        }

        if (accessCamera != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.CAMERA);
        }

        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, REQUEST_CODE_LOC);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        if (sharedPreferencesUtils != null){
            email = sharedPreferencesUtils.readStringPreference(Constant.REF_EMAIL, "");
            password = sharedPreferencesUtils.readStringPreference(Constant.REF_PASSWORD, "");

            txt_email.setText(email);
            txt_password.setText(password);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onNetWorkError(String msg) {
        hideProgress();
        showMessage(msg);
    }

    @Override
    public void onLoginError(String msg) {
        hideProgress();
        showMessage(msg);
    }

    @Override
    public void onLoginSuccess(String token, Garage garage) {
        //Process return main
        hideProgress();
        Utils.APP_TOKEN = token;
        if (sharedPreferencesUtils != null && garage != null){
            sharedPreferencesUtils.writeStringPreference(Constant.REF_EMAIL, email);
            sharedPreferencesUtils.writeStringPreference(Constant.REF_PASSWORD, password);
            sharedPreferencesUtils.writeStringPreference(Constant.PREF_DEVICE_TOKEN, token);
            sharedPreferencesUtils.writeIntegerPreference(Constant.GARAGE_ID, garage.getId());
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onEmailError(String msg) {
        hideProgress();
        txt_email.setError(msg);
        txt_email.requestFocus();
    }

    @Override
    public void onPasswordError(String msg) {
        hideProgress();
        txt_password.setError(msg);
        txt_password.requestFocus();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_forGotPassWord)
    public void processForgotPassword(){
        //todo process forgot password
        showMessage("Chức năng này đang được hoàn thiện.");
    }

    @OnClick(R.id.btn_login)
    public void processLogin(){
        //Get data from UI
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();
        showProgress();
        //createTimeOut(3000);
        //todo process login
        processLogin(email, password, Utils.FCM_TOKEN);
    }

    private void processLogin(String email, String password, String fcmToken) {
        if (!Utils.isNetworkAvailable(this)){
            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }

        LoginResponseListener listener = new LoginResponseListener();
        LoginCallAPI callAPI = new LoginCallAPI();
        callAPI.processLogin(email, password, fcmToken, listener);
    }

    private class LoginResponseListener extends OnResponseListener<LoginResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            onLoginError("Có lỗi xảy ra, vui lòng thử lại.");
            super.onErrorResponse(error);
        }

        @Override
        public void onResponse(LoginResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                onLoginSuccess(response.getLogin().getToken(), response.getLogin().getGarage());
                showMessage(response.getMessage());
            }else {
                onLoginError(response.getMessage());
            }
        }
    }

    /**
     * The method used to hide the keyboard
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOC:
                if (grantResults.length > 0) {
                    for (int gr : grantResults) {
                        // Check if request is granted or not
                        if (gr != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }

                    //TODO - Add your code here to start Discovery

                }
                break;
            default:
                return;
        }
    }

}
