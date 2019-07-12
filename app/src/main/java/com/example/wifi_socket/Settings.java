package com.example.wifi_socket;

import android.graphics.Color;

public class Settings {

    public static int send_me;
    public static int send_accepted;
    public static int send_error;
    public static int colorAccent;
    public static int error_color;
    public static int bottomsheetcolor;
    public static int background_color;
    public static String name;





    public Settings() {
        send_me=Color.rgb(64,121,121);
        send_accepted =Color.rgb(112,112,112);
        send_error=Color.rgb(135,74,88);
        colorAccent=Color.rgb(10,155,141);
        error_color=Color.rgb(200,70,80);
        bottomsheetcolor=Color.rgb(200,200,200);
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
}
