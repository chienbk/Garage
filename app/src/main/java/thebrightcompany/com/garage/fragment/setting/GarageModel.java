package thebrightcompany.com.garage.fragment.setting;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.List;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class GarageModel implements Serializable {

    public int id;
    public String name;
    public List<String> phone;
    public String email;
    public String address;
    public String website;
    public int rate;
    public String lat;
    public String lng;
    public int rate_count;
    public String description;
    public String image;
    public int isKGara;


    public String getPhoneString(){
        if(phone == null || phone.size()==0)return "";
        String result = "";
        for (String s : phone){
            result += s + ", ";
        }
        return result.substring(0, result.length()-2);

    }
}
