package com.example.anas.assingment3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Switch;

public class WifiReceiver extends BroadcastReceiver {

    String network = "mysettings";
    private static final String TAG = "MTAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Boolean state = wifiManager.isWifiEnabled();

        SharedPreferences s = context.getSharedPreferences(network,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean("wifi",state);
        editor.commit();

        //for changing state of switch in app
        Switch wifiButton = ((Activity) context).findViewById(R.id.wifiSwitch);
        wifiButton.setChecked(state);

        Log.d(TAG, "MYRECEIVER: " + state);
}
}
