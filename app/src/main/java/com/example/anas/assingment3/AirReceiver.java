package com.example.anas.assingment3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.widget.Switch;
import android.widget.ToggleButton;

public class AirReceiver extends BroadcastReceiver {

    private static final String TAG = "MTAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAirplaneMode = Settings.Global.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        String network = "mysettings";
        SharedPreferences s = context.getSharedPreferences(network,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean("air",isAirplaneMode);
        editor.commit();

        //for changing state of switch in app
        ToggleButton airButton = ((Activity) context).findViewById(R.id.airplaneSwitch);
        airButton.setChecked(isAirplaneMode);
        //changing because wifi switch off in airplane mode
        Switch wifiButton = ((Activity) context).findViewById(R.id.wifiSwitch);
        wifiButton.setChecked(false);
    }
}
