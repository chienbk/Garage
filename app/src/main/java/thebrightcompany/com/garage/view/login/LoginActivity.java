package thebrightcompany.com.garage.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.SharedPreferencesUtils;
import thebrightcompany.com.garage.view.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.email)
    EditText txt_email;
    @BindView(R.id.password)
    EditText txt_password;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Method use to init view
     */
    private void initView() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferencesUtils != null){
            email = sharedPreferencesUtils.readStringPreference(Constant.REF_EMAIL, "");
            password = sharedPreferencesUtils.readStringPreference(Constant.REF_PASSWORD, "");

            txt_email.setText(email);
            txt_password.setText(password);
        }
    }

    @Override
    public void onNetWorkError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onLoginError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onLoginSuccess(String token) {
        //Process return main
        hideProgress();
        if (sharedPreferencesUtils != null){
            sharedPreferencesUtils.writeStringPreference(Constant.REF_EMAIL, email);
            sharedPreferencesUtils.writeStringPreference(Constant.REF_PASSWORD, password);
            sharedPreferencesUtils.writeStringPreference(Constant.TOKEN, token);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onEmailError(String msg) {
        txt_email.setError(msg);
        txt_email.requestFocus();
    }

    @Override
    public void onPasswordError(String msg) {
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
    }

    @OnClick(R.id.btn_login)
    public void processLogin(String emailUser, String passwordUser){
        //Get data from UI
        emailUser = txt_email.getText().toString();
        passwordUser = txt_password.getText().toString();
        this.email = emailUser;
        this.password = passwordUser;
        showProgress();
        //todo process login

    }

    /**
     * Use to hide progress
     * @param time
     */
    private void createTimeOut(long time){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                onLoginSuccess("");
            }
        }, time );
    }
}
