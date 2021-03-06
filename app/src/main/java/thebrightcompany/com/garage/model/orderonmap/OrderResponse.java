package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderResponse implements Serializable{

    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataOfOrder dataOfOrder;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOfOrder getDataOfOrder() {
        return dataOfOrder;
    }

    public void setDataOfOrder(DataOfOrder dataOfOrder) {
        this.dataOfOrder = dataOfOrder;
    }
}
