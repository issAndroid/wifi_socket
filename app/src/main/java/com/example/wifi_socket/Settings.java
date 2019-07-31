package com.example.wifi_socket;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class Settings {
    public static int send_me;
    public static int send_accepted;
    public static int send_error;
    public static int text_color1;
    public static int text_color2;
    public static int file_text_color1;
    public static int file_text_color2;
    public static int file_send_me;
    public static int file_send_accepted;
    public static int file_send_error;
    public static int colorAccent;
    public static int btn_edittext_color1;
    public static int btn_edittext_color2;
    public static int error_color;
    public static int bottomsheetcolor;
    public static int background_color;
    public static int scanpage_background_color;
    public static String my_name;
    public static String mess_port;
    public static String file_port;
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    public Settings() {
        preferences = MainActivity.context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = preferences.edit();
        // for message
        send_me = preferences.getInt("send_me", Color.rgb(64, 121, 121));
        send_accepted = preferences.getInt("send_accepted", Color.rgb(112, 112, 112));
        send_error = preferences.getInt("send_error", Color.rgb(135, 74, 88));
        //1 title   2 context
        text_color1 = preferences.getInt("text_color1", Color.rgb(200, 200, 200));
        text_color2 = preferences.getInt("text_color2", Color.rgb(250, 250, 250));
        //for file
        file_send_me = preferences.getInt("file_send_me", Color.rgb(64, 121, 121));
        file_send_accepted = preferences.getInt("file_send_accepted", Color.rgb(112, 112, 112));
        file_send_error = preferences.getInt("file_send_error", Color.rgb(135, 74, 88));
        // 1 title   2 context
        file_text_color1 = preferences.getInt("file_text_color1", Color.rgb(60, 60, 60));
        file_text_color2 = preferences.getInt("file_text_color2", Color.rgb(0, 0, 0));
        colorAccent = preferences.getInt("colorAccent", Color.rgb(10, 155, 141));
        error_color = preferences.getInt("error_color", Color.rgb(200, 70, 80));
        bottomsheetcolor = preferences.getInt("bottomsheetcolor", Color.rgb(200, 200, 200));
        background_color = preferences.getInt("background_color", Color.rgb(150, 150, 150));
        scanpage_background_color = preferences.getInt("scanpage_background_color", Color.rgb(100, 100, 100));
        mess_port = preferences.getString("mess_port", "5000");
        file_port = preferences.getString("file_port", "4000");
        my_name = preferences.getString("my_name", "Test");
        //color for btn send, settings, edittexts
        btn_edittext_color1 = preferences.getInt("btn_edittext_color1",Color.rgb(10,10,10));
        btn_edittext_color2 = preferences.getInt("btn_edittext_color2",Color.rgb(190,190,190));
    }
    public static void setBottomsheetcolor(int bottomsheetcolor) {
        editor.putInt("bottomsheetcolor", bottomsheetcolor);
        editor.apply();
    }
    public static void setSend_me(int send_me) {
        editor.putInt("send_me", send_me);
        editor.apply();
    }
    public static void setError_color(int error_color) {
        editor.putInt("error_color", error_color);
        editor.apply();
    }
    public static void setSend_accepted(int send_accepted) {
        editor.putInt("send_accepted", send_accepted);
        editor.apply();
    }
    public static void setSend_error(int send_error) {
        editor.putInt("send_error", send_error);
        editor.apply();
    }
    public static void setFile_text_color1(int file_text_color1) {
        editor.putInt("file_text_color1", file_text_color1);
        editor.apply();
    }
    public static void setFile_text_color2(int file_text_color2) {
        editor.putInt("file_text_color2", file_text_color2);
        editor.apply();
    }
    public static void setFile_send_me(int file_send_me) {
        editor.putInt("file_send_me", file_send_me);
        editor.apply();

    }
    public static void setFile_send_accepted(int file_send_accepted) {
        editor.putInt("file_send_accepted", file_send_accepted);
        editor.apply();

    }
    public static void setFile_send_error(int file_send_error) {
        editor.putInt("file_send_error", file_send_error);
        editor.apply();
    }
    public static void setScanpage_background_color(int scanpage_background_color) {
        editor.putInt("scanpage_background_color", scanpage_background_color);
        editor.apply();
    }
    public static void setMess_port(String mess_port) {
        editor.putString("mess_port", mess_port);
        editor.apply();
    }
    public static void setFile_port(String file_port) {
        editor.putString("file_port", file_port);
        editor.apply();
    }
    public static void setColorAccent(int colorAccent) {
        editor.putInt("colorAccent", colorAccent);
        editor.apply();
    }
    public static void setBackground_color(int background_color) {
        editor.putInt("background_color", background_color);
        editor.apply();
    }
    public static void setMy_name(String my_name) {
        editor.putString("my_name", my_name);
        editor.apply();
    }
    public static void setText_color1(int text_color1) {
        editor.putInt("text_color1", text_color1);
        editor.apply();
    }
    public static void setText_color2(int text_color2) {
        editor.putInt("text_color2", text_color2);
        editor.apply();
    }
    public static void setBtn_edittext_color1(int btn_edittext_color1) {
        editor.putInt("btn_edittext_color1", btn_edittext_color1);
        editor.apply();
    }
    public static void setBtn_edittext_color2(int btn_edittext_color2) {
        editor.putInt("btn_edittext_color2", btn_edittext_color2);
        editor.apply();
    }
    public static int getColor(String s) {
        if (s.equals("send_me"))
            return send_me;
        else if (s.equals("send_accepted"))
            return send_accepted;
        else if (s.equals("send_error"))
            return send_error;
        else if (s.equals("text_color1"))
            return text_color1;
        else if (s.equals("text_color2"))
            return text_color2;
        else if (s.equals("file_text_color1"))
            return file_text_color1;
        else if (s.equals("file_text_color2"))
            return file_text_color2;
        else if (s.equals("file_send_me"))
            return file_send_me;
        else if (s.equals("file_send_accepted"))
            return file_send_accepted;
        else if (s.equals("file_send_error"))
            return file_send_error;
        else if (s.equals("colorAccent"))
            return colorAccent;
        else if (s.equals("error_color"))
            return error_color;
        else if (s.equals("bottomsheetcolor"))
            return bottomsheetcolor;
        else if (s.equals("background_color"))
            return background_color;
        else if (s.equals("scanpage_background_color"))
            return scanpage_background_color;
        else if (s.equals("btn_edittext_color1"))
            return btn_edittext_color1;
        else if (s.equals("btn_edittext_color2"))
            return btn_edittext_color2;
        else return 0;
    }
    public static void setColor(String s, int color){
         if (s.equals("send_me"))
            setSend_me(color);
        else if (s.equals("send_accepted"))
            setSend_accepted(color);
        else if (s.equals("send_error"))
             setSend_error(color);
        else if (s.equals("text_color1"))
            setText_color1(color);
        else if (s.equals("text_color2"))
            setText_color2(color);
        else if (s.equals("file_text_color1"))
            setFile_text_color1(color);
        else if (s.equals("file_text_color2"))
            setFile_text_color2(color);
        else if (s.equals("file_send_me"))
            setFile_send_me(color);
        else if (s.equals("file_send_accepted"))
            setFile_send_accepted(color);
        else if (s.equals("file_send_error"))
            setFile_send_error(color);
        else if (s.equals("colorAccent"))
            setColorAccent(color);
        else if (s.equals("error_color"))
            setError_color(color);
        else if (s.equals("bottomsheetcolor"))
            setBottomsheetcolor(color);
        else if (s.equals("background_color"))
            setBackground_color(color);
        else if (s.equals("scanpage_background_color"))
            setScanpage_background_color(color);
         else if (s.equals("btn_edittext_color1"))
             setBtn_edittext_color1(color);
         else if (s.equals("btn_edittext_color2"))
             setBtn_edittext_color2(color);
    }
}
