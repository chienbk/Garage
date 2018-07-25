package thebrightcompany.com.garage.api.logout;


import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.model.login.LoginResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LogoutCallAPI {

    public void processLogout(String token, String fcm_token, OnResponseListener<LoginResponse> listener){
        LogoutRequest request = new LogoutRequest(listener);
        request.setDeviceToken(token);
        request.setFcmToken(fcm_token);
        App.addRequest(request, "Logout");
    }
}
