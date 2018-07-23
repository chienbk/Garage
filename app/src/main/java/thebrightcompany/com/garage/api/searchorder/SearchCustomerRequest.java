package thebrightcompany.com.garage.api.searchorder;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.model.searchcustomer.SearchCustomerResponse;
import thebrightcompany.com.garage.utils.Constant;

public class SearchCustomerRequest extends BaseGetRequest<SearchCustomerResponse> {
    public SearchCustomerRequest(OnResponseListener<SearchCustomerResponse> listener, String phone, String token) {

        super(String.format(Constant.URL_SEARCH_USER, phone, token), new TypeToken<SearchCustomerResponse>() {
        }.getType(), listener);
        Log.d("GetOrdersRequest", "url: " + String.format(Constant.URL_SEARCH_USER, phone, token));
    }
}
