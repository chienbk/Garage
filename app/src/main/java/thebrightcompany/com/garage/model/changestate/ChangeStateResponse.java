package thebrightcompany.com.garage.model.changestate;

import com.google.gson.annotations.SerializedName;

public class ChangeStateResponse {
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataChangeState dataChangeState;

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

    public DataChangeState getDataChangeState() {
        return dataChangeState;
    }

    public void setDataChangeState(DataChangeState dataChangeState) {
        this.dataChangeState = dataChangeState;
    }
}
