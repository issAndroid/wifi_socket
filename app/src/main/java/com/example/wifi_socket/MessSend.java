package com.example.wifi_socket;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MessSend extends AsyncTask {

    Handler handler = new Handler();
    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            Socket s = new Socket(MainActivity.get_ip(),MainActivity.get_port());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            PrintWriter pw = new PrintWriter(dos);
            pw.println(MainActivity.get_mess());
            pw.close();s.close();

            handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    UserMessages.add_message(MainActivity.context.getResources().getString(R.string.send_me),MainActivity.get_mess());
                }
            });


        } catch (IOException e) {
            handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    UserMessages.add_message(MainActivity.context.getResources().getString(R.string.send_error),MainActivity.get_mess());
                }
            });
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.message.setText("");
            }
        });
        return null;
    }
}
