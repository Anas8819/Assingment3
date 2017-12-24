package com.example.anas.assingment3;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";
    String network = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(network,MODE_PRIVATE);
        Switch wifiButton = (Switch) findViewById(R.id.wifiSwitch);
        ToggleButton airplaneState = (ToggleButton) findViewById(R.id.airplaneSwitch);
        TextView batteryShow = (TextView) findViewById(R.id.batteryShow);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;
        int batteryState = (int) (batteryPct*100);
        Log.d(TAG, "onCreate: " + batteryState);
        if(batteryState<=15){
            batteryShow.setText("Low");
        }
        else {
            batteryShow.setText("Okay");
        }
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean state = wifiManager.isWifiEnabled();
        wifiButton.setChecked(state);
        wifiManager.setWifiEnabled(state);

        Boolean a = sharedPreferences.getBoolean("air",true);
        airplaneState.setChecked(a);

        wifiButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wifiManager.setWifiEnabled(b);
                editor.putBoolean("wifi",b);
                editor.apply();
            }
        });
        WifiReceiver forWifi = new WifiReceiver();
        IntentFilter filterWifi = new IntentFilter();
        filterWifi.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(forWifi, filterWifi);

        AirReceiver forAirplane = new AirReceiver();
        IntentFilter filterAir = new IntentFilter();
        filterAir.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(forAirplane, filterAir);
    }

    @Override
    protected void onStop() {
        super.onStop();
        WifiReceiver x = new WifiReceiver();
        unregisterReceiver(x);
        AirReceiver y = new AirReceiver();
        unregisterReceiver(y);
    }
}
