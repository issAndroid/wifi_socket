package com.example.wifi_socket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
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
            Toast.makeText(MainActivity.context,MainActivity.context.getResources().getString(R.string.not_listen) , Toast.LENGTH_SHORT).show();
        }else {
            m.set_Color(Settings.send_accepted,false);
        }
        list.add(m);
        MainActivity.mess_list.setAdapter(new MyGraidViewAdapter(list));
        MainActivity.mess_list.invalidate();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void add_file(String ip, String filename, boolean clickable){
        final FileView fv = new FileView(MainActivity.context);
        fv.set_text_color(Settings.file_text_color1, Settings.file_text_color2);
        fv.setTextView(ip,filename);

        if (clickable){
            fv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openfile(fv.filename.getText().toString());
                }
            });
        }

        if (ip.equals(MainActivity.context.getResources().getString(R.string.send_me))){
            fv.set_Color(Settings.file_send_me,true);
        }else if (ip.equals(MainActivity.context.getResources().getString(R.string.send_error))) {
            fv.set_Color(Settings.file_send_error, true);
            Toast.makeText(MainActivity.context, MainActivity.context.getResources().getString(R.string.not_listen), Toast.LENGTH_SHORT).show();
        }else if (ip.equals(MainActivity.context.getResources().getString(R.string.rec_error))){
            fv.set_Color(Settings.file_send_error, true);
            Toast.makeText(MainActivity.context,  MainActivity.context.getResources().getString(R.string.not_received), Toast.LENGTH_SHORT).show();
        }else {
            fv.set_Color(Settings.file_send_accepted,false);
        }
        list.add(fv);
        MainActivity.mess_list.setAdapter(new MyGraidViewAdapter(list));
        MainActivity.mess_list.invalidate();
    }


    public static void openfile(String s){
        String filepath = Environment.getExternalStorageDirectory().getPath()+s;
        Uri uri = Uri.fromFile(new File(filepath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "resource/folder");
         try {
             MainActivity.context.startActivity(intent);
         }catch (Exception e){
             Toast.makeText(MainActivity.context,MainActivity.context.getResources().getString(R.string.file_ex) , Toast.LENGTH_LONG).show();
         }

    }

}
