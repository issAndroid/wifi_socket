package com.example.wifi_socket;

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
    public static int error_color;
    public static int bottomsheetcolor;
    public static int background_color;
    public static int scanpage_background_color;
    public static String my_name;
    public static String mess_port;
    public static String file_port;






    public Settings() {
        // for message
        send_me=Color.rgb(64,121,121);
        send_accepted =Color.rgb(112,112,112);
        send_error=Color.rgb(135,74,88);
        //1 title   2 context
        text_color1=Color.rgb(200,200,200);
        text_color2=Color.rgb(250,250,250);
        //for file
        file_send_me=Color.rgb(64,121,121);
        file_send_accepted =Color.rgb(112,112,112);
        file_send_error=Color.rgb(135,74,88);
        // 1 title   2 context
        file_text_color1=Color.rgb(50,50,50);
        file_text_color2=Color.rgb(0,0,0);



        colorAccent=Color.rgb(10,155,141);
        error_color=Color.rgb(200,70,80);
        bottomsheetcolor=Color.rgb(200,200,200);
        background_color=Color.rgb(150,150,150);

        scanpage_background_color=Color.rgb(60,60,60);

        mess_port="5000";
        file_port="4000";

        my_name="Test";
    }


    public static void setBottomsheetcolor(int bottomsheetcolor) {
        Settings.bottomsheetcolor = bottomsheetcolor;
    }

    public static void setSend_me(int send_me) {
        Settings.send_me = send_me;
    }

    public static void setError_color(int error_color) {
        Settings.error_color = error_color;
    }

    public static void setSend_accepted(int send_accepted) {
        Settings.send_accepted = send_accepted;
    }

    public static void setSend_error(int send_error) {
        Settings.send_error = send_error;
    }

    public static void setColorAccent(int colorAccent) {
        Settings.colorAccent = colorAccent;
    }

    public static void setBackground_color(int background_color) {
        Settings.background_color = background_color;
    }

    public static void setMy_name(String my_name) {
        Settings.my_name = my_name;
    }
}
