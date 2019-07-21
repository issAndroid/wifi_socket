package com.example.wifi_socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;

public class ScanPage extends AppCompatActivity {

    WifiApManager wifiApManager;
    static GridView grid_dev;
    static ArrayList<WifiDevView> list;
    static Button ref;
    String dev_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);
        init();
        scan();
        ref();
    }

    private void ref() {
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAll(list);
                scan();
            }
        });
    }

    private void init() {
        wifiApManager=new WifiApManager(MainActivity.context);
        ref=(Button)findViewById(R.id.ref);
        ref.setText(getResources().getString(R.string.refresh));
        grid_dev=(GridView) findViewById(R.id.grid_dev);
        list=new ArrayList<>();
    }


    private void scan() {
        MainActivity.devices.removeAll(MainActivity.devices);
        wifiApManager.getClientList(false, new FinishScanListener() {

            @Override
            public void onFinishScan(final ArrayList<ClientScanResult> clients) {
                ArrayList<WifiDevView> arrayList = new ArrayList();
                for (ClientScanResult clientScanResult : clients) {
                    final WifiDevView wifiDevView = new WifiDevView(MainActivity.context);
                    wifiDevView.setValues(dev_name,clientScanResult.getIpAddr());
                    wifiDevView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                               MainActivity.http.setText(wifiDevView.ippp.getText().toString());
                               finish();
                        }
                    });
                    arrayList.add(wifiDevView);
                }
                for (WifiDevView w:arrayList){
                    w.set_name_dev(dev_name);
                    list.add(w);
                    set_devices_name();
                }

            }
        });
    }


    public void set_devices_name(){

        for (int i=0; i<list.size(); i++) {
            MessRes.commandHandler.get_target_name(MainActivity.getLocalIpAddress(),Settings.mess_port,list.get(i).ippp.getText().toString(),i);

            if (list.get(i).dev.getText().toString().equals(""))
                list.get(i).dev.setText("ناشناخته");
            grid_dev.setAdapter(new MyGraidViewAdapter(list));
        }
    }

}

