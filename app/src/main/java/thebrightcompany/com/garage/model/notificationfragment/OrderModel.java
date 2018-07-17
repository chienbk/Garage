package thebrightcompany.com.garage.model.notificationfragment;

import java.io.Serializable;
import java.util.List;

import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class OrderModel implements Serializable{

    public String id;
    public String customer_id;

    public CustomerModel customer_data;

    public String garage_id;
    public String customer_info;
    public String code;
    public String customer_code;
    public String time_create;
    public String end_time;
    public String address;
    public String phone;
    public String lat;
    public String lng;
    public String note;
    public String status;

    public List<TroubleModel> trouble_code;

    public int getNumberTrouble(){
        if(trouble_code == null) return 0;
        return trouble_code.size();
    }


}
