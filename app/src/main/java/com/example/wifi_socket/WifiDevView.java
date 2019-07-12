package com.example.wifi_socket;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

class WifiDevView extends LinearLayout {

    LinearLayout rootView;
    TextView dev,ippp;

    public WifiDevView(Context context) {
        super(context);
        init(context);
    }



    public WifiDevView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        rootView= (LinearLayout) inflate(context,R.layout.wifi_dev_view,this);
        dev= rootView.findViewById(R.id.dev);
        ippp= rootView.findViewById(R.id.ipppp);
    }


    public void setValues(String dev, String ippp){
        this.dev.setText("Device: "+dev);
        this.ippp.setText(ippp);
    }

}
