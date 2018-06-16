package com.example.manish_pc.speedcalculator;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyLocationUpdate.MyLocationChangeListener {
    private TextView tvSpeed;
    private LocationManager locationManager;
    private MyLocationUpdate myLocationUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSpeed = findViewById(R.id.tv_speed);
        tvSpeed.setText("Move your device");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationUpdate = new MyLocationUpdate(this, locationManager);
    }

    @Override
    public void myLocationChange(Location location) {
        Log.d("location", "" + location.getSpeed());
        float speed = location.getSpeed();  // speed in m/s
        updateUI(speed);
    }

    private void updateUI(float s) {
        float speed = myLocationUpdate.round(s, 2);
        float speedInKmph = myLocationUpdate.mpsToKmph(speed);
        float speedInKmphUpTo2DecimalPlaces = myLocationUpdate.round(speedInKmph, 2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("" + speed + " m/s");
        stringBuilder.append("\n" + speedInKmphUpTo2DecimalPlaces + " km/hr");
        tvSpeed.setText(stringBuilder.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(myLocationUpdate);
        }
    }
}
