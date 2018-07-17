package thebrightcompany.com.garage.view.addcustomer;

import java.util.List;

import thebrightcompany.com.garage.model.searchcustomer.Customer;
import thebrightcompany.com.garage.view.BaseView;

public interface CreateOrderView extends BaseView{

    void onSearchCustomerSuccess(String token, List<Customer> customers);
    void onAddOrderSuccess(String msg);
    void onAddOrderError(int status_code, String msg);
    void onSearchCustomerError(String msg);
    void onNetWorkError(String msg);
    void onNameError(String msg);
    void onPhoneError(String msg);
    void onAddressError(String msg);
}
