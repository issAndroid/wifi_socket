package com.example.wifi_socket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    public static boolean wifi_state=false, hotspot_state=false, ip_send_set=false;
    static EditText http, message, myname;
    Button send, scan, file;
    static Context context;
    static GridView mess_list;
    static UserMessages userMessages;
    View bottomsheet;
    BottomSheetBehavior bottomSheetBehavior;
    static TextView your_ip;
    public static String target_name;
    static CoordinatorLayout layout;
    static ArrayList<String> devices;
    static ArrayList<String> my_sends,my_rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        Settings themeColor = new Settings();
        init();
        send();
        res();
        scanpage();
        connection_state();
        startService(new Intent(getBaseContext(), NetworkRes.class));
    }

    private void connection_state() {

        your_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection_state();
            }
        });

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int apState = 0;
        try {
            apState = (Integer) wifiManager.getClass().getMethod("getWifiApState").invoke(wifiManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (apState == 13) {
            // Ap Enabled
            hotspot_state=true;
            your_ip.setText(getLocalIpAddress());
            your_ip.setTextColor(Settings.colorAccent);

        }else {

            hotspot_state=false;
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
                // wifi conected
                wifi_state=true;
                your_ip.setText(getLocalIpAddress());
                your_ip.setTextColor(Settings.colorAccent);


            }else {
                wifi_state=false;
                your_ip.setText(context.getResources().getString(R.string.not_connected));
                your_ip.setTextColor(Settings.error_color);

            }

        }

    }

    private void scanpage() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScanPage.class));
                res();
            }
        });
    }

    private void res() {
        Thread thread = new Thread(new MessRes());
        thread.start();
    }

    private void send() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = http.getText().toString();
                if (ip.equals("")){
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.receiver_not_found), Toast.LENGTH_SHORT).show();
                }else if (ip.equals(your_ip.getText().toString())) {
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.yourself), Toast.LENGTH_SHORT).show();
                }else {
                    MessSend m = new MessSend();
                    m.execute("true",get_mess(),get_ip(),Settings.mess_port,"false");
                }
            }
        });
    }

    private  void init() {
        send = (Button) findViewById(R.id.send);
        scan = (Button) findViewById(R.id.scan);
        file = (Button) findViewById(R.id.file);
        http = (EditText) findViewById(R.id.http);
        myname = (EditText) findViewById(R.id.myname);
        message = (EditText) findViewById(R.id.message);
        layout=(CoordinatorLayout) findViewById(R.id.coorlayout);
        mess_list = (GridView) findViewById(R.id.list_mess);
        your_ip=(TextView) findViewById(R.id.your_ip);
        devices=new ArrayList<>();
        userMessages=new UserMessages();
        View bottomsheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomsheet.setBackgroundColor(Settings.bottomsheetcolor);
        layout.setBackgroundColor(Settings.background_color);
        mess_list.setBackgroundColor(Settings.background_color);

        if (getLocalIpAddress() != null)
            your_ip.setText(getLocalIpAddress());
        else your_ip.setText(context.getResources().getString(R.string.not_connected));

        resize();
        myname_listener();
        mkdir();



        // for test
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(chooseFile, "Choose a file"), 11);
            }
        });


    }

    private void myname_listener() {
        myname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Settings.setMy_name(myname.getText().toString());
            }
        });
    }

    private void resize() {
        double h = (double) getDisplyHeight();
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight((int) Math.floor(0.1*h)+20);

        message.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence cs, int s, int c, int a) {}

            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                if (message.getLineCount()>1)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().contains("wlan")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                            .hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && (inetAddress.getAddress().length == 4)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {}
        return null;
    }

    public static String get_ip() {
        return http.getText().toString();
    }

    public static String get_mess() {
        return message.getText().toString();

    }

    public int getDisplyHeight(){
        final WindowManager windowManager=getWindowManager();
        final Point size = new Point();
        int screenhight =0, actionbar=0,statusbar = 0;
        if (getActionBar() != null)
            actionbar=getActionBar().getHeight();
        int r = getResources().getIdentifier("status_bar_height","dimen","android");
        if (r>0){
            statusbar=getResources().getDimensionPixelSize(r);
        }
        int contop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenhight=size.y;
        return screenhight-contop-actionbar-statusbar;
    }

    private void mkdir() {
        // for creating app's directory
        File exdir = Environment.getExternalStorageDirectory();
        File dir = new File(exdir,"WifiSocket");
        if (! dir.exists()){
            dir.mkdirs();
            Toast.makeText(context, "created", Toast.LENGTH_SHORT).show();
        }
    }

    public void send_file() {
        for (String s : my_sends) {
            File file = new File(s);
            String command = "***file***"+file.getAbsolutePath()+"*"+file.length()+"*"+getLocalIpAddress();
            MessSend m = new MessSend();
            m.execute("false", command , get_ip(), Settings.file_port, "true");
            FileSend fileSend = new FileSend();
            fileSend.execute(s,get_ip(),Settings.file_port);
        }
    }






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        String ip = http.getText().toString();
        if (ip.equals("")) {
            Toast.makeText(MainActivity.this, context.getResources().getString(R.string.receiver_not_found), Toast.LENGTH_SHORT).show();
        } else if (ip.equals(your_ip.getText().toString())) {
            Toast.makeText(MainActivity.this, context.getResources().getString(R.string.yourself), Toast.LENGTH_SHORT).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 11 && resultCode == RESULT_OK && data != null) {

                if (data.getClipData() != null) {

                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {

                        Uri file_pa = data.getClipData().getItemAt(i).getUri();
                        my_sends.add(file_pa.getPath());
                    }
                } else if (data.getData() != null) {

                    Uri imgUri = data.getData();
                    my_sends.add(imgUri.getPath());
                }
            }
            send_file();
        }
    }



}
