package thebrightcompany.com.garage.model;

/**
 * Created by ChienNv9 on 3/15/2018.
 */

public class LatLongMessage {
    private double lat;
    private double lng;

    public LatLongMessage(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
