package thebrightcompany.com.garage.model.notificationfragment;

import java.io.Serializable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class CustomerModel implements Serializable{

    public String customerId;
    public String full_name;
    public String phone;
    public String address;
    public String avartar;

    public String typeOfCar;

    public String licenseOfCar;

    public String state;

    public String lat;

    public String lng;

    public String email;


    public CustomerModel(){

    }


}
