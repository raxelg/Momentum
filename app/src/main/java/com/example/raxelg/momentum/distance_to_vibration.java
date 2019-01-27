package com.example.raxelg.momentum;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;
import android.widget.Toast;

import java.text.DecimalFormat;


public class distance_to_vibration {

    public static double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in Km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c* 3280.8399;
        return valueResult ;
    }



    public static int vibration_strength(double distance) {

        //return (int) ((-1 * distance * (255.0 / 30.0)) + 255);

        int vibrationStrength;

        if(distance < 5 && distance >= 0){
            vibrationStrength = 255;
        } else if(distance >= 5 && distance < 10){
            vibrationStrength = 204;
        } else if(distance >= 10 && distance < 15){
            vibrationStrength = 153;
        } else if(distance >= 15 && distance < 20){
            vibrationStrength = 102;
        } else if (distance >= 20 && distance <= 30){
            vibrationStrength = 51;
        } else{
            vibrationStrength = 0;
        }
        return vibrationStrength;
    }

    public static int frequency(double distance) {

        //return (int) ((-1 * distance * (255.0 / 30.0)) + 255);

        int frequency;

        if(distance < 5 && distance >= 0){
            frequency= 250;
        } else if(distance >= 5 && distance < 10){
            frequency = 500;
        } else if(distance >= 10 && distance < 15){
            frequency = 1000;
        } else if(distance >= 15 && distance < 20){
            frequency = 1500;
        } else if (distance >= 20 && distance <= 30){
            frequency = 2000;
        } else{
            frequency = 0;
        }
        return frequency;
    }

}
