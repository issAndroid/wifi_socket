package com.example.wifi_socket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkRes extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {
            int apState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            if (apState == 13) {
                // Hotspot AP is enabled
                MainActivity.hotspot_state = true;
                MainActivity.your_ip.setText(MainActivity.getLocalIpAddress());
                MainActivity.your_ip.setTextColor(ThemeColor.colorAccent);

            } else {
                // Hotspot AP is disabled/not ready
                MainActivity.hotspot_state = false;
                if (!MainActivity.wifi_state) {
                    MainActivity.your_ip.setText("Not Connected");
                    MainActivity.your_ip.setTextColor(ThemeColor.error_color);
                }

            }

        }

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            MainActivity.wifi_state = true;
            MainActivity.your_ip.setText(MainActivity.getLocalIpAddress());
            MainActivity.your_ip.setTextColor(ThemeColor.colorAccent);

        }else {
            MainActivity.wifi_state = false;
            if (!MainActivity.hotspot_state) {
                MainActivity.your_ip.setText("Not Connected");
                MainActivity.your_ip.setTextColor(ThemeColor.error_color);
            }
        }

    }
}
