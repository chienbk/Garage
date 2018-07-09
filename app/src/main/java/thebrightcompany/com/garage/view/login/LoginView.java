package thebrightcompany.com.garage.view.login;

import thebrightcompany.com.garage.model.login.Garage;
import thebrightcompany.com.garage.view.BaseView;

public interface LoginView extends BaseView{
    void onNetWorkError(String msg);
    void onLoginError(String msg);
    void onLoginSuccess(String token, Garage garage);
    void onEmailError(String msg);
    void onPasswordError(String msg);
}
