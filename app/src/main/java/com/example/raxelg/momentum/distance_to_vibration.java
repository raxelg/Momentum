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


public class distance_to_vibration {

    public static double distanceBetweenInFt(double lat1, double lon1, double lat2, double lon2) { //lat1 and lon1 are the location of the phone
        double R = 6371; //radius of earth in km
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double x = dlon * Math.cos(lat1 + lat2) / 2;

        //1km=3,280.839895ft

        return 3280.839895* R * Math.sqrt(x * x + dlat * dlat);

    }


    public static int vibration_strength(double distance) {

        return (int) (-1 * distance * (255.0 / 30.0)) + 255;

    }


}
