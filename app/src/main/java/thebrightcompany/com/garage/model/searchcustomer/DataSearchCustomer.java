package thebrightcompany.com.garage.model.searchcustomer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataSearchCustomer implements Serializable{
    @SerializedName("token")
    private String token;
    @SerializedName("customers")
    private List<Customer> customers;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
