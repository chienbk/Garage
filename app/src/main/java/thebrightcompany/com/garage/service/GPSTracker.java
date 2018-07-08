package thebrightcompany.com.kdoctor.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

import thebrightcompany.com.kdoctor.model.garage.LatLongMessage;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;

/**
 * Created by ChienNv9 on 3/15/2018.
 */
public class GPSTracker extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    public static final String TAG = GPSTracker.class.getSimpleName();

    public static double lat;
    public static double lng;

    private GoogleApiClient mGoogleApiClient;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        stopLocationUpdates();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000);
        //locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Intent intent = new Intent(Contains.GPS_FILTER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        lat = location.getLatitude();
        lng = location.getLongitude();
        setLat(location.getLatitude());
        setLng(location.getLongitude());
        if (sharedPreferencesUtils != null){
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_LAT, lat +"");
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_LNG, lng + "");
        }
        EventBus.getDefault().post(new LatLongMessage(lat, lng));

        Log.d(TAG, "Lat: " + location.getLatitude());
        Log.d(TAG, "Long: " + location.getLongitude());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        GPSTracker.lng = lng;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        GPSTracker.lat = lat;
    }
}
