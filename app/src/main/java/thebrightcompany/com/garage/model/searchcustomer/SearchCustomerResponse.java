package thebrightcompany.com.garage.model.searchcustomer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchCustomerResponse implements Serializable{
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataSearchCustomer dataSearchCustomer;

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

    public DataSearchCustomer getDataSearchCustomer() {
        return dataSearchCustomer;
    }

    public void setDataSearchCustomer(DataSearchCustomer dataSearchCustomer) {
        this.dataSearchCustomer = dataSearchCustomer;
    }
}
