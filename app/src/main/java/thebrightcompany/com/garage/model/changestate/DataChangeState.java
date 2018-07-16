package thebrightcompany.com.garage.model.changestate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataChangeState implements Serializable{
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
