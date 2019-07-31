package com.example.wifi_socket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;



public class MainActivity extends AppCompatActivity {

    public static boolean wifi_state = false, hotspot_state = false, sending_state = false;
    static EditText http, message, myname;
    Button scan;
    ImageButton send, settings;
    static ImageButton file;
    static Context context;
    static GridView mess_list;
    static UserMessages userMessages;
    BottomSheetBehavior bottomSheetBehavior;
    static TextView your_ip;
    public static String target_name;
    static CoordinatorLayout layout;
    static ArrayList<String> devices;
    static ArrayList<String> my_sends;
    static ProgressBar progressBar;
    static Settings themeColor;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
         themeColor= new Settings();
        init();
        send();
        res();
        scanpage();
        connection_state();
        startService(new Intent(getBaseContext(), NetworkRes.class));
        settings_page();
    }
    private void settings_page() {
        this.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color12(settings);
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });
    }
    @SuppressLint("ResourceAsColor")
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
            hotspot_state = true;
            your_ip.setText(getLocalIpAddress());
            your_ip.setTextColor(Settings.colorAccent);

        } else {

            hotspot_state = false;
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
                // wifi conected
                wifi_state = true;
                your_ip.setText(getLocalIpAddress());
                your_ip.setTextColor(Settings.colorAccent);


            } else {
                wifi_state = false;
                your_ip.setText(context.getResources().getString(R.string.not_connected));
                your_ip.setTextColor(Settings.error_color);

            }

        }

    }
    private void scanpage() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScanPage.class));
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
                color12(send);
                String ip = http.getText().toString();
                if (ip.equals("")) {
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.receiver_not_found), Toast.LENGTH_SHORT).show();
                } else if (ip.equals(your_ip.getText().toString())) {
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.yourself), Toast.LENGTH_SHORT).show();
                } else {
                    MessSend m = new MessSend();
                    m.execute("true", get_mess(), get_ip(), Settings.mess_port, "false");
                }
            }
        });
    }


    public void color12 (final ImageButton ib){
        ib.setColorFilter(Settings.btn_edittext_color2);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ib.setColorFilter(Settings.btn_edittext_color1);
            }
        },50);
    }


    private void init() {
        isStoragePermissionGranted();
        send = (ImageButton) findViewById(R.id.send);
        
        
        // first login
        first_use();
        
        settings = (ImageButton) findViewById(R.id.settings);
        scan = (Button) findViewById(R.id.scan);
        scan.setText(getResources().getString(R.string.scan));
        file = (ImageButton) findViewById(R.id.file);
        http = (EditText) findViewById(R.id.http);
        myname = (EditText) findViewById(R.id.myname);
        message = (EditText) findViewById(R.id.message);
        layout = (CoordinatorLayout) findViewById(R.id.coorlayout);
        mess_list = (GridView) findViewById(R.id.list_mess);
        your_ip = (TextView) findViewById(R.id.your_ip);
        progressBar = findViewById(R.id.progressBar2);
        devices = new ArrayList<>();
        userMessages = new UserMessages();
        View bottomsheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomsheet.setBackgroundColor(Settings.bottomsheetcolor);
        layout.setBackgroundColor(Settings.background_color);
        mess_list.setBackgroundColor(Settings.background_color);
        my_sends = new ArrayList<>();
        if (getLocalIpAddress() != null)
            your_ip.setText(getLocalIpAddress());
        else your_ip.setText(context.getResources().getString(R.string.not_connected));
        resize();
        myname.setText(Settings.my_name);
        myname_listener();
        mkdir();
        pick_file_to_send();
        set_imagebutton_color();
    }

    private void set_imagebutton_color() {
        this.send.setColorFilter(Settings.btn_edittext_color1);
        this.file.setColorFilter(Settings.btn_edittext_color1);
        this.settings.setColorFilter(Settings.btn_edittext_color1);
        editttext_colors(message);
        editttext_colors(http);
        editttext_colors(myname);
    }

    public void editttext_colors(EditText e){
            e.getBackground().setColorFilter(Settings.edittext_linecolor, PorterDuff.Mode.SRC_ATOP);
            e.setTextColor(Settings.edittext_textcolor);
            e.setHintTextColor(Settings.edittext_hintcolor);
    }

    private void first_use() {
        preferences=getSharedPreferences("app",Context.MODE_PRIVATE);
        String s = preferences.getString("first","");
        if (s.equals("")){
            // to do... first login
            showTextDialog();
            // saving login
            editor=preferences.edit();
            editor.putString("first","no");
            editor.apply();
        }
    }
    private void showTextDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("اولین ورود");

        builder.setMessage(context.getResources().getString(R.string.first));

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name  = input.getText().toString();
                Settings.setMy_name(name);
                myname.setText(name);
            }
        });

        builder.show();
    }
    private void pick_file_to_send() {
        //for picking file , adding to send list and call send method
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color12(file);
                String ip = http.getText().toString();
                if (ip.equals("")) {
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.receiver_not_found), Toast.LENGTH_SHORT).show();
                } else if (ip.equals(your_ip.getText().toString())) {
                    Toast.makeText(MainActivity.this, context.getResources().getString(R.string.yourself), Toast.LENGTH_SHORT).show();
                } else {


                    DialogProperties properties = new DialogProperties();
                    properties.selection_mode = DialogConfigs.MULTI_MODE;
                    properties.root = new File(Environment.getExternalStorageDirectory().getPath());
                    FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
                    dialog.setTitle(context.getResources().getString(R.string.select_a_file));

                    dialog.setDialogSelectionListener(new DialogSelectionListener() {
                        @Override
                        public void onSelectedFilePaths(String[] files) {
                            //files is the array of the paths of files selected by the Application User.
                            for (String s : files) {
                                if (s != null) {
                                    my_sends.add(s);
                                }
                            }
                            file.setClickable(false);
                            send_file();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Toast.makeText(context,context.getResources().getString(R.string.m), Toast.LENGTH_SHORT).show();
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(context, "ok!!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    private void myname_listener() {
        myname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Settings.setMy_name(myname.getText().toString());
            }
        });
    }
    private void resize() {
        double h = (double) getDisplyHeight();
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight((int) Math.floor(0.1 * h)+20);

        message.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence cs, int s, int c, int a) {
            }

            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                if (message.getLineCount() > 1)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

                if (message.getText().toString().equals("")) {
                    file.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                } else {
                    send.setVisibility(View.VISIBLE);
                    file.setVisibility(View.GONE);
                }

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
        } catch (SocketException ex) {
        }
        return null;
    }
    public static String get_ip() {
        return http.getText().toString();
    }
    public static String get_mess() {
        return message.getText().toString();

    }
    public int getDisplyHeight() {
        final WindowManager windowManager = getWindowManager();
        final Point size = new Point();
        int screenhight = 0, actionbar = 0, statusbar = 0;
        if (getActionBar() != null)
            actionbar = getActionBar().getHeight();
        int r = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (r > 0) {
            statusbar = getResources().getDimensionPixelSize(r);
        }
        int contop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenhight = size.y;
        return screenhight - contop - actionbar - statusbar;
    }
    private void mkdir() {
        // for creating app's directory
        File exdir = Environment.getExternalStorageDirectory();
        File dir = new File(exdir, "WifiSocket");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // for creating image music and ...
        //pictures
        File file1 = new File(Environment.getExternalStorageDirectory().getPath()+"/WifiSocket");
        File file11 = new File(file1, "Picture");
        if (!file11.exists()) {
            file11.mkdirs();
        }
        //musics
        File file2 = new File(Environment.getExternalStorageDirectory().getPath()+"/WifiSocket");
        File file22 = new File(file2, "Music");
        if (!file22.exists()) {
            file22.mkdirs();
        }
        //videos
        File file3 = new File(Environment.getExternalStorageDirectory().getPath()+"/WifiSocket");
        File file33 = new File(file3, "Video");
        if (!file33.exists()) {
            file33.mkdirs();
        }
        //other
        File file4 = new File(Environment.getExternalStorageDirectory().getPath()+"/WifiSocket");
        File file44 = new File(file4, "Other");
        if (!file44.exists()) {
            file44.mkdirs();
        }
        //document
        File file5 = new File(Environment.getExternalStorageDirectory().getPath()+"/WifiSocket");
        File file55 = new File(file5, "Document");
        if (!file55.exists()) {
            file55.mkdirs();
        }
    }
    public static void send_file() {
        String path = my_sends.get(0);
        File help = new File(path);
        int si = (int) help.length();
        String command = "***file***" + after_last_slash(my_sends.get(0)) + "*" + si + "*" + getLocalIpAddress();
        MessSend m = new MessSend();
        //sending command
        m.execute("false", command, get_ip(), Settings.mess_port, "true");
        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        //sending file
        FileSend fileSend = new FileSend();
        fileSend.execute(path, get_ip());
        my_sends.remove(0);
    }
    public static String after_last_slash(String s) {
        int t = s.length() - 1;
        while (s.charAt(t) != '/' && t > 0) {
            t--;
        }
        return s.substring(t + 1);
    }
}
