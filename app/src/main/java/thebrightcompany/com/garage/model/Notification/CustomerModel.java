package thebrightcompany.com.garage.model.Notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerModel implements Serializable{

    @SerializedName("customer_id")
    public String customerId;

    @SerializedName("nameOfCustomer")
    public String nameOfCustomer;

    @SerializedName("phone")
    public String phone;

    @SerializedName("address")
    public String address;

    @SerializedName("typeOfCar")
    public String typeOfCar;

    @SerializedName("liceseOfCar")
    public String licenseOfCar;

    @SerializedName("state")
    public String state;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lng")
    public String lng;

    @SerializedName("email")
    public String email;


    public List<TroubleModel> troubles;

}
