package thebrightcompany.com.garage.api.login;


import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.model.login.LoginResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LoginCallAPI {

    public void processForgotPassword(String email, String password, String token, OnResponseListener<LoginResponse> listener){
        LoginRequest request = new LoginRequest(listener);
        request.setEmail(email);
        request.setPassword(password);
        request.setDeviceToken(token);
        App.addRequest(request, "Login");
    }
}
