package com.example.wifi_socket;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.ArrayList;

public class UserMessages {

    static ArrayList<MessageView> list = new ArrayList<>();

    public UserMessages() {}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void add_message(String ip, String message){
        MessageView m = new MessageView(MainActivity.context);
        m.setTextView(ip,message);
        if (ip.equals(MainActivity.context.getResources().getString(R.string.send_me))){
            m.set_Color(Settings.send_me,true);
        }else if (ip.equals(MainActivity.context.getResources().getString(R.string.send_error))){
            m.set_Color(Settings.send_error,true);
            Toast.makeText(MainActivity.context, "کسی گوش نمیده!", Toast.LENGTH_SHORT).show();
        }else {
            m.set_Color(Settings.send_accepted,false);
        }
        list.add(m);
        MainActivity.mess_list.setAdapter(new MyGraidViewAdapter(list));
        MainActivity.mess_list.invalidate();
    }
}
