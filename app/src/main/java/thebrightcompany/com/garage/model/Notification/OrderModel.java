package thebrightcompany.com.garage.model.Notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable{

    @SerializedName("order_id")
    public String orderId;


    public CustomerModel customer;

    @SerializedName("customer_id")
    public String customerId;

    @SerializedName("time_create")
    public String timeCreate;

    @SerializedName("state")
    public String state;


}
