package com.example.lightsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Path path;
    long befortime;
    int i=1;
    ImageView imgHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0]<proximitySensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener,proximitySensor,2*1000 *1000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //ham doi hinh
            LacMH(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void setEvent() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);

    }

    private void setControl()
    {
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
    }
    public void LacMH(SensorEvent event) {
        float[] value = event.values;
        float a = value[0];
        float b = value[1];
        
        float vector = (a * a + b * b) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if (vector >= 2) {
            long lasttime = event.timestamp;
            if ((lasttime - befortime) > 400) {
                i++;
                if (i == 1) {
                    imgHinh.setImageResource(R.drawable.dt1);
                }
                if (i == 2) {
                    imgHinh.setImageResource(R.drawable.dt2);
                }
                if( i == 5)
                {
                    imgHinh.setImageResource(R.drawable.dt3);
                }
                if(i == 6)
                {
                    imgHinh.setImageResource(R.drawable.dt4);
                }
                if(i == 7)
                {
                    imgHinh.setImageResource(R.drawable.dt5);
                    i =1;
                }
            }
            befortime = lasttime;
        }
    }
}
