package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author ChienBK
 */
public class Order implements Serializable{
    @SerializedName("order_id")
    private int order_id;
    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("nameOfCustomer")
    private String nameOfCustomer;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("typeOfCar")
    private String typeOfCar;
    @SerializedName("licenseOfCar")
    private String licenseOfCar;
    @SerializedName("state")
    private int state;
    @SerializedName("time")
    private String time;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("cost")
    private String cost;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("email")
    private double email;
    @SerializedName("trouble_code")
    private List<TroubleCode> trouble_code;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    public String getLicenseOfCar() {
        return licenseOfCar;
    }

    public void setLicenseOfCar(String licenseOfCar) {
        this.licenseOfCar = licenseOfCar;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public double getEmail() {
        return email;
    }

    public void setEmail(double email) {
        this.email = email;
    }

    public List<TroubleCode> getTrouble_code() {
        return trouble_code;
    }

    public void setTrouble_code(List<TroubleCode> trouble_code) {
        this.trouble_code = trouble_code;
    }
}
