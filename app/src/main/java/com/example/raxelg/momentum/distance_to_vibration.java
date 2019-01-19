package com.example.raxelg.momentum;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;


public class distance_to_vibration {

    public static double distanceBetween(double lat1, double lon1, double lat2,double lon2) {
        double R = 6371; //radius of earth in km
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);


        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double x = dlon * Math.cos(lat1 + lat2) / 2;


        double distance = R * Math.sqrt(x * x + dlat * dlat);
        return distance;


    }


    protected void vibration_strength(int distance) { //takes in distance at a given point
                                                      //converts it into vibration strength
                                                    //distance will be a certain range (0-30?)
                                                    //vibration inreases intensity every 5

    }
}
