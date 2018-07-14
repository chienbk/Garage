package thebrightcompany.com.garage.api.changestateorder;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.model.changestate.ChangeStateResponse;
import thebrightcompany.com.garage.utils.Constant;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ChangeStateOrderRequest extends BasePostRequest<ChangeStateResponse> {

    public ChangeStateOrderRequest(OnResponseListener<ChangeStateResponse> listener, String id) {
        super(String.format(Constant.URL_UPDATE_STATE, id), new TypeToken<ChangeStateResponse>() {
        }.getType(), listener);
    }

    public void setStatus(String status){
        setParam("status", status);
    }

    public void setToken(String token){
        setParam("token", token);
    }
}
