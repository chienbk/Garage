package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable{
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;
    @SerializedName("orders")
    private List<Order> orders;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
