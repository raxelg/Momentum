package com.example.raxelg.momentum;


import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;

public class MainActivity extends AppCompatActivity {

    Button vibrateButton;
    Vibrator vibrator;
    int distance = 0;
    long[] mVibratePattern = new long[]{0, 1000, 1000, 1000, 1000, 1000};
    int[] mAmplitudes = new int[]{0, 25, 75, 125, 175, 255};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrateButton = findViewById(R.id.vibrateButton);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        vibrateButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (vibrator.hasAmplitudeControl()) {
                        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, 0);
                        vibrator.vibrate(effect);
                    }
                }else{
                    vibrator.vibrate(400);
                }
            }
        }
        );
    }

}
