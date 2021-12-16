package com.example.sensor_baru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected DBCahaya dbCahaya;
    protected SensorManager SM;
    protected Sensor lightsensor;
    protected TextView txCahaya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txCahaya = findViewById(R.id.txCahaya);
        SM = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        if(SM.getDefaultSensor(Sensor.TYPE_LIGHT)!=null){
            lightsensor = SM.getDefaultSensor(Sensor.TYPE_LIGHT);
            txCahaya.setText("0");

            findViewById(R.id.bt_Start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //register event
                    //delay interval sensing
                    int delay = SensorManager.SENSOR_DELAY_NORMAL;
                    SM.registerListener(lightListener,lightsensor,delay);
                }
            });

            findViewById(R.id.bt_Stop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //unregister event listener
                    SM.unregisterListener(lightListener);
                }
            });

        }else{
            txCahaya.setText("Sensor NA");
        }
    }

    private SensorEventListener lightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            dbCahaya = new DBCahaya(getBaseContext());
            String s = String.valueOf(sensorEvent.values[0]);
            txCahaya.setText(s);

            //Mendapatkan Repository dengan Mode Menulis
            SQLiteDatabase data = dbCahaya.getWritableDatabase();

            //Membuat Map Baru, Yang Berisi Nama Kolom dan Data Yang Ingin Dimasukan
            ContentValues values = new ContentValues();
            values.put(DBCahaya.MyColumns.Nilai, s.toString());

            data.insert(DBCahaya.MyColumns.NamaTabel, null, values);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}