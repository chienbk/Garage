package thebrightcompany.com.garage.api.addorder;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BasePostRequest;
import thebrightcompany.com.garage.model.addorder.AddOrderResponse;
import thebrightcompany.com.garage.utils.Constant;

/**
 * Created by ChienNV on 10/25/16.
 */

public class AddOrderRequest extends BasePostRequest<AddOrderResponse> {

    public AddOrderRequest(OnResponseListener<AddOrderResponse> listener) {
        super(Constant.URL_CREATE_ORDER, new TypeToken<AddOrderResponse>() {
        }.getType(), listener);
    }

    public void setIdOfCustomer(String id){
        setParam("idOfCustomer", id);
    }

    public void setName(String name){
        setParam("name", name);
    }

    public void setPhone(String phone){
        setParam("phone", phone);
    }

    public void setEmail(String email){
        setParam("email", email);
    }

    public void setTypeOrCar(String typeOrCar){
        setParam("typeOfCar", typeOrCar);
    }

    public void setLisenceOfCar(String lisenceOfCar){
        setParam("lisenceOfCar", lisenceOfCar);
    }

    public void setAddress(String address){
        setParam("address", address);
    }

    public void setNote(String note){
        setParam("note", note);
    }

    public void setTroubleCode(String troubleCode){
        setParam("troublecode", troubleCode);
    }

    public void setToken(String token){
        setParam("token", token);
    }
}
