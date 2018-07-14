package thebrightcompany.com.garage.model.Notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TroubleModel implements Serializable{

    @SerializedName("code")
    public String code;

    @SerializedName("description")
    public String description;
}
