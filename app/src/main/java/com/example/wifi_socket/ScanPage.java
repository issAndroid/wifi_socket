package com.example.wifi_socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;

public class ScanPage extends AppCompatActivity {

    WifiApManager wifiApManager;
    GridView grid_dev;
    ArrayList<WifiDevView> list;
    Button ref;

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
        grid_dev=(GridView) findViewById(R.id.grid_dev);
        list = new ArrayList();
    }


    private void scan() {
        wifiApManager.getClientList(false, new FinishScanListener() {

            @Override
            public void onFinishScan(final ArrayList<ClientScanResult> clients) {
                ArrayList<WifiDevView> arrayList = new ArrayList();
                for (ClientScanResult clientScanResult : clients) {
                    final WifiDevView wifiDevView = new WifiDevView(MainActivity.context);
                    wifiDevView.setValues(clientScanResult.getDevice(),clientScanResult.getIpAddr());
                    wifiDevView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(ScanPage.this, wifiDevView.ippp.getText().toString(), Toast.LENGTH_SHORT).show();
                               MainActivity.http.setText(wifiDevView.ippp.getText().toString());
                               finish();
                        }
                    });
                    arrayList.add(wifiDevView);
                }
                for (WifiDevView w:arrayList){
                    list.add(w);
                    grid_dev.setAdapter(new MyGraidViewAdapter(list));
                }
            }
        });
    }



}

