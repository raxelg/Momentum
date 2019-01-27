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
        //double km = valueResult / 1;
        //DecimalFormat newFormat = new DecimalFormat("####");
        //int kmInDec = Integer.valueOf(newFormat.format(km));
        //double meter = valueResult % 1000;
        //int meterInDec = Integer.valueOf(newFormat.format(meter));
        //Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                //+ " Meter   " + meterInDec);

        return valueResult ;
    }



    public static int vibration_strength(double distance) {

        return (int) ((-1 * distance * (255.0 / 30.0)) + 255);

    }


}
