package thebrightcompany.com.garage.api.profile;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.model.login.LoginResponse;
import thebrightcompany.com.garage.utils.Constant;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ProfileRequest extends BasePostRequest<LoginResponse> {

    public ProfileRequest(OnResponseListener<LoginResponse> listener) {
        super(Constant.URL_LOGIN, new TypeToken<LoginResponse>() {
        }.getType(), listener);
    }

    public void setEmail(String email) {
        setParam("email", email);
    }
    public void setPassword(String password){
        setParam("password", password);
    }
    public void setDeviceToken(String token){
        setParam("device_token", token);
    }
}
