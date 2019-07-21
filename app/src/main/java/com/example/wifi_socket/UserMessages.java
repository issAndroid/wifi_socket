package com.example.wifi_socket;

import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.ArrayList;

public class UserMessages {

    static ArrayList list = new ArrayList<>();

    public UserMessages() {}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void add_message(String ip, String message){
        MessageView m = new MessageView(MainActivity.context);
        m.setTextView(ip,message);
        m.set_text_colot(Settings.text_color1,Settings.text_color2);
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


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void add_file(String ip, String filename){
        FileView fv = new FileView(MainActivity.context);
        fv.set_text_color(Settings.file_text_color1, Settings.file_text_color2);
        fv.setTextView(ip,filename);
        if (ip.equals(MainActivity.context.getResources().getString(R.string.send_me))){
            fv.set_Color(Settings.file_send_me,true);
        }else if (ip.equals(MainActivity.context.getResources().getString(R.string.send_error))) {
            fv.set_Color(Settings.file_send_error, true);
            Toast.makeText(MainActivity.context, "کسی گوش نمیده!", Toast.LENGTH_SHORT).show();
        }else if (ip.equals(MainActivity.context.getResources().getString(R.string.rec_error))){
            fv.set_Color(Settings.file_send_error, true);
            Toast.makeText(MainActivity.context, "دریافت نشد", Toast.LENGTH_SHORT).show();
        }else {
            fv.set_Color(Settings.file_send_accepted,false);
        }
        list.add(fv);
        MainActivity.mess_list.setAdapter(new MyGraidViewAdapter(list));
        MainActivity.mess_list.invalidate();
    }

}
