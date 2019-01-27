package com.example.raxelg.momentum;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    //Initial objects
    private Vibrator vibrator;
    private TextView textView;
    private FusedLocationProviderClient client;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallBack;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private boolean updatesOn = true;
    private TextView distanceTextView;
    private TextView vibrationTextView;
    double lat2 = 42.355139;
    double long2 = -1*71.1008134;
    float [] results = new float[5];
    int vibrationPower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Necessary object identification/authorization
        textView = findViewById(R.id.coordinateText);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        distanceTextView = findViewById(R.id.distanceText);
        vibrationTextView = findViewById(R.id.vibrationText);

        client = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mLastLocation = location;
                        textView.setText("Longitude: " + String.valueOf(location.getLatitude()) + "Latitude: " + String.valueOf(location.getLatitude()));
                    }
                }
            });
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }

        locationCallBack = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for(Location location: locationResult.getLocations()){
                    if(location != null){
                        textView.setText("Longitude: " + String.valueOf(location.getLongitude()) + "\n" + "Latitude: " + String.valueOf(location.getLatitude()));
                        location.distanceBetween(location.getLatitude(), location.getLongitude(), lat2,long2,results);
                        distanceTextView.setText("Distance: " + String.valueOf(results[0]*3.28084));
                        vibrationPower = distance_to_vibration.vibration_strength(results[0] * 3.28084);
                        vibrationTextView.setText(String.valueOf(vibrationPower));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (vibrator.hasAmplitudeControl()) {

                                    VibrationEffect pattern = VibrationEffect.createWaveform(new long[]{1000, 2000}, new int[]{0, 0, distance_to_vibration.vibration_strength(results[0] * 3.28084)}, 1);
                                    vibrationTextView.setText(String.valueOf(vibrationPower));
                                    vibrator.vibrate(pattern);

                            } else {
                                vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 4}, -1));
                            }
                        }
                    }
                }
            }
        };

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_FINE_LOCATION:

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted do nothing and carry on

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (updatesOn) startLocationUpdates();

    }

    private void startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            client.requestLocationUpdates(mLocationRequest,locationCallBack,null);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

}
