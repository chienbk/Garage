package thebrightcompany.com.garage.fragment;

import java.util.List;

import thebrightcompany.com.garage.model.orderonmap.Order;
import thebrightcompany.com.garage.view.BaseView;

public interface CustomerView extends BaseView{
    void onNetWorkError(String msg);
    void onGetCustomerSuccess(String token, List<Order> orders);
    void onGetCustomerError(String msg);
    void onChangeStateError(String msg);
    void onChangeStateSuccess(String token, String msg);
}
