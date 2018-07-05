package thebrightcompany.com.garage.view.login;

import thebrightcompany.com.garage.view.BaseView;

public interface LoginView extends BaseView{
    void onNetWorkError(String msg);
    void onLoginError9(String msg);
    void onLoginSuccsess(String token);
    void onEmailError(String msg);
    void onPasswordError(String msg);
}
