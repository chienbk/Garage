package thebrightcompany.com.garage.api.addorder;


import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.model.addorder.AddOrderResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class AddOrderCallAPI {

    public void processAddOrder(String idOfCustomer, String name, String phone,
                                String email, String typeOfCar, String lisenceOfCar,
                                String address, String note, String troubleCode,
                                String token, OnResponseListener<AddOrderResponse> listener){

        AddOrderRequest request = new AddOrderRequest(listener);
        request.setIdOfCustomer(idOfCustomer);
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setTypeOrCar(typeOfCar);
        request.setLisenceOfCar(lisenceOfCar);
        request.setAddress(address);
        request.setNote(note);
        request.setTroubleCode(troubleCode);
        request.setToken(token);
        App.addRequest(request, "Add Order");
    }
}
