package thebrightcompany.com.garage.api.changestateorder;


import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.model.changestate.ChangeStateResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ChangeStateCallAPI {

    public void processChangeState(int orderId, int status, String token, OnResponseListener<ChangeStateResponse> listener){

        ChangeStateOrderRequest request = new ChangeStateOrderRequest(listener, String.valueOf(orderId));
        request.setStatus(String.valueOf(status));
        request.setToken(token);
        App.addRequest(request, "Change State");
    }
}
