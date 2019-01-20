package com.example.raxelg.momentum;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //Initial objects
    private Button vibrateButton;
    private Vibrator vibrator;
    TextView textView;
    private LocationManager locationManager;
    String provider;

    //Hard-coded variables
    long[] mVibratePattern = new long[]{0, 1000, 1000, 1000, 1000, 1000};
    int[] mAmplitudes = new int[]{0, 25, 75, 125, 175, 255};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Necessary object identification/authorization
        textView = findViewById(R.id.coordinateText);
        vibrateButton = findViewById(R.id.vibrateButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        //Button vibrates continuously
        vibrateButton.setOnClickListener(new Button.OnClickListener() {
                                             public void onClick(View v) {
                                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                     if (vibrator.hasAmplitudeControl()) {
                                                         VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, 0);
                                                         vibrator.vibrate(effect);
                                                     }
                                                 } else {
                                                     vibrator.vibrate(400);
                                                 }
                                             }
                                         }
        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        onLocationChanged(location);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        textView.setText("Longitude: " + longitude + "\n" + "Latitude: " + latitude);
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
}
