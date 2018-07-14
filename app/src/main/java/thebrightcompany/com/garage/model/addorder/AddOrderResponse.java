package thebrightcompany.com.garage.model.addorder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddOrderResponse implements Serializable{
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
}
