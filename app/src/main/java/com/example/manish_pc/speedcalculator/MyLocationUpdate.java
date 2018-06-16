package com.example.manish_pc.speedcalculator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.math.BigDecimal;

/**
 * Created by Manish-Pc on 16/06/2018.
 */


/**
 * To Use this class
 * 1. Create Object of LocationManager in Activity.
 * LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 * <p>
 * 2. Create object of this class and pass locationManager object and context
 * MyLocationUpdate myLocation = new MyLocationUpdate(this, locationManager);
 * <p>
 * 3. Implement MyLocationChangeListener interface in the Activity you want location update
 * <p>
 * DONE !!!!!!
 * <p>
 * IMPORTANT POINTS
 * 1. LocationManager will continuously update current location of the Device even after closing the App.
 * If you don't want to Location updates from background then -
 * Use locationManager object and myLocation object to stop location updates.
 * locationManager.removeUpdates(myLocation) in onDestroy() of Activity
 * <p>
 * <p>
 * 2. Add following permissions
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * <p>
 * <p>
 * 3. You can get speed of the Device -
 * float speed = location.getSpeed() // speed in meter/second
 * <p>
 * 4. Use round() method for showing result in required decimal places.
 * <p>
 * 5. Use mpsToKmph() method to convert speed from m/s into kmph.
 */

public class MyLocationUpdate implements LocationListener {
    private Activity mActivityReference;

    MyLocationUpdate(Context context, LocationManager locationManager) {
        mActivityReference = (Activity) context;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mActivityReference,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 39);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("speed", "" + location.getSpeed());
        // TODO: 16/06/2018 Change Activity Name
        if (mActivityReference instanceof MainActivity) {
            ((MainActivity) mActivityReference).myLocationChange(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface MyLocationChangeListener {
        void myLocationChange(Location location);
    }

    /**
     * @param d
     * @param decimalPlace
     * @return
     */
    public float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * @param mps
     * @return
     */
    public float mpsToKmph(float mps) {
        return mps * 18 / 5;
    }
}