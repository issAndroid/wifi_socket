package com.example.wifi_socket;


import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class MessRes extends Thread implements Runnable{
    static Socket sockets;
    static ServerSocket sss;
    static InputStreamReader isr;
    static BufferedReader br;
    Handler handler=new Handler();


    @Override
    public void run() {
        super.run();
        try {
            sss=new ServerSocket(MainActivity.get_port());
            while (true){
                sockets=sss.accept();
                isr=new InputStreamReader(sockets.getInputStream());
                br=new BufferedReader(isr);
                final String str = alllines(br);

                handler.post(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        String ip=MainActivity.get_ip();
                        if (ip.equals(""))
                            UserMessages.add_message("Ip: "+MainActivity.get_ip(),str);
                        else
                            UserMessages.add_message("Ip: Unknown",str);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public String alllines(BufferedReader br) throws IOException {
        String out = br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            out+="\n"+line;
        }
        br.close();

        return out;
    }
}
