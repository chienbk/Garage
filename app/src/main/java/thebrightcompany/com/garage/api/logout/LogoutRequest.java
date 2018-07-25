package thebrightcompany.com.garage.api.logout;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.model.login.LoginResponse;
import thebrightcompany.com.garage.utils.Constant;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LogoutRequest extends BasePostRequest<LoginResponse> {

    public LogoutRequest(OnResponseListener<LoginResponse> listener) {
        super(Constant.URL_LOGOUT, new TypeToken<LoginResponse>() {
        }.getType(), listener);
    }

    public void setFcmToken(String fcm_token){
        setParam("device_token", fcm_token);
    }
    public void setDeviceToken(String token){
        setParam("token", token);
    }
}
