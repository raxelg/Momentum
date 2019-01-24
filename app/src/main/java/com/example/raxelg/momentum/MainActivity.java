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

import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //Initial objects
    private Button vibrateButton;
    private Button stopButton;
    private Vibrator vibrator;
    TextView textView;
    private LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Necessary object identification/authorization
        textView = findViewById(R.id.coordinateText);
        vibrateButton = findViewById(R.id.vibrateButton);
        stopButton = findViewById(R.id.b_stop);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


        /*    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }*/
        //Location location = locationManager.getLastKnownLocation(provider);

        stopButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                vibrator.cancel();
            }
        });




        //Button vibrates continuously
        vibrateButton.setOnClickListener(new Button.OnClickListener() {
                                             public void onClick(View v) {
                                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                     if (vibrator.hasAmplitudeControl()) {

                                                         //permission for location

                                                         if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                                                 != PackageManager.PERMISSION_GRANTED &&
                                                                 ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                                                         != PackageManager.PERMISSION_GRANTED) {
                                                             return;
                                                         }

                                                         Location location;
                                                         double lat1, lon1, lat2, lon2, distance;
                                                         int vibrationStrength;
                                                         VibrationEffect pattern;
                                                         Random carPosition = new Random();

                                                         do{
                                                             location = locationManager.getLastKnownLocation(provider);
                                                             onLocationChanged(location);
                                                             lat1 = location.getLatitude();
                                                             lon1 = location.getLongitude();
                                                             lat2 = lat1;
                                                             //rangeMin + (rangeMax - rangeMin) * r.nextDouble(); Cambridge values
                                                             lon2 = -71.1 + (0.1 * carPosition.nextDouble());

                                                             distance = distance_to_vibration.distanceBetweenInFt(lat1, lon1, lat2, lon2);

                                                             vibrationStrength = distance_to_vibration.vibration_strength(distance);
                                                             pattern = VibrationEffect.createWaveform(new long[] {0, 1000}, new int[]{vibrationStrength,0}, -1);
                                                             vibrator.vibrate(pattern);

                                                        } while(distance_to_vibration.distanceBetweenInFt(lat1, lon1, lat2, lon2) > 2 || distance_to_vibration.distanceBetweenInFt(lat1, lon1, lat2, lon2) <= 30);

                                                     }
                                                 } else {
                                                     vibrator.vibrate(400);
                                                 }
                                             }
                                         }
        );


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
