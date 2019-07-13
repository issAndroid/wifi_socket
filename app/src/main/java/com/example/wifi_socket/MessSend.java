package com.example.wifi_socket;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MessSend extends AsyncTask <String,Void,Void> {

    Handler handler = new Handler();
    @Override
    protected Void doInBackground(final String... strings) {
        final Boolean is_show = Boolean.valueOf(strings[0]);
        boolean is_command=Boolean.valueOf(strings[4]);


        try {
            Socket s = new Socket(strings[2],Integer.valueOf(strings[3]));
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            PrintWriter pw = new PrintWriter(dos);
            if (is_command)
            pw.println(strings[1]);
            else pw.println(strings[1]+"\n////"+MainActivity.getLocalIpAddress());
            pw.close();s.close();


            if (is_show) {
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        UserMessages.add_message(MainActivity.context.getResources().getString(R.string.send_me), strings[1]);
                    }
                });

            }
           } catch (IOException e) {
                handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    if (is_show)
                    UserMessages.add_message(MainActivity.context.getResources().getString(R.string.send_error), strings[1]);
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
