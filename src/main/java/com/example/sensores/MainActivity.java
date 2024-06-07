package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
SensorManager sensorManager;
Sensor sensor;
SensorEventListener sensorEventListener;
ImageView logo;
TextView logoText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Guardamos el layout de la app
        setContentView(R.layout.activity_main);
        logo = (ImageView) findViewById(R.id.logo);
        logoText = (TextView) findViewById(R.id.logoText);

        //Obtenemos los sensores
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //Si no hay sensor terminamos la aplicacion
        if(sensor==null){
            System.out.println("No hay sensor");
            Toast.makeText(getApplicationContext(), "Sensor no disponible, cerrando",Toast.LENGTH_LONG).show();
            finish();
        }


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float ligthLevel = event.values[0];
                System.out.println("Luz =" + ligthLevel);
                if(ligthLevel<70){
                    //Modo oscuro con poca luz
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(10,20,25));
                    logo.setImageResource(R.drawable.moon);
                    logoText.setText("Buenas noches");
                }
                else if (ligthLevel<2000) {
                    //Modo claro con luz normal
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(138,138,138));
                    logo.setImageResource(R.drawable.sun);
                    logoText.setText("Buenos dias");
                }
                else {
                    //Modo de demasiada luz
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,255));
                    logo.setImageResource(R.drawable.ashenbaby);
                    logoText.setText("aaaaaaaaaaa");

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    //Liberamos el sensor al detener la app
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    //Obtenemos el sensor al iniciar la app
    @Override
    protected void onResume(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
}