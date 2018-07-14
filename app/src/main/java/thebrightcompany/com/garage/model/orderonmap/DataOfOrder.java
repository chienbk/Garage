package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataOfOrder implements Serializable{
    @SerializedName("token")
    private String token;
    @SerializedName("orders")
    private List<Order> orders;

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
