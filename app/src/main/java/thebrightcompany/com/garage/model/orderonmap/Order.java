package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author ChienBK
 */
public class Order implements Serializable{
    @SerializedName("id")
    private int id;
    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("garage_id")
    private String garage_id;
    @SerializedName("customer_info")
    private String customer_info;
    @SerializedName("code")
    private String code;
    @SerializedName("customer_code")
    private String customer_code;
    @SerializedName("time_create")
    private String time_create;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("note")
    private String note;
    @SerializedName("status")
    private int status;
    @SerializedName("trouble_code")
    private List<TroubleCode> troubleCodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getGarage_id() {
        return garage_id;
    }

    public void setGarage_id(String garage_id) {
        this.garage_id = garage_id;
    }

    public String getCustomer_info() {
        return customer_info;
    }

    public void setCustomer_info(String customer_info) {
        this.customer_info = customer_info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TroubleCode> getTroubleCodes() {
        return troubleCodes;
    }

    public void setTroubleCodes(List<TroubleCode> troubleCodes) {
        this.troubleCodes = troubleCodes;
    }
}
