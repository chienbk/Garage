package thebrightcompany.com.garage.view.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import thebrightcompany.com.garage.R;

public class LoginActivity extends AppCompatActivity implements LoginView{

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

    }

    @Override
    public void onNetWorkError(String msg) {

    }

    @Override
    public void onLoginError9(String msg) {

    }

    @Override
    public void onLoginSuccsess(String token) {

    }

    @Override
    public void onEmailError(String msg) {

    }

    @Override
    public void onPasswordError(String msg) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {

    }
}
