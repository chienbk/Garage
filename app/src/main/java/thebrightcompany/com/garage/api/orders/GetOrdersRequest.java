package thebrightcompany.com.garage.api.orders;

import com.google.gson.reflect.TypeToken;


import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.model.orderonmap.OrderResponse;
import thebrightcompany.com.garage.utils.Constant;

public class GetOrdersRequest extends BaseGetRequest<OrderResponse> {
    public GetOrdersRequest(OnResponseListener<OrderResponse> listener, String token) {

        super(String.format(Constant.URL__CUSTOMER_ON_MAP, token), new TypeToken<OrderResponse>() {
        }.getType(), listener);
    }
}
