package com.example.wifi_socket;


import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
    static CommandHandler commandHandler;


    @Override
    public void run() {
        super.run();
        try {
            sss=new ServerSocket(Integer.valueOf(Settings.mess_port));
            while (true){
                sockets=sss.accept();
                isr=new InputStreamReader(sockets.getInputStream());
                br=new BufferedReader(isr);
                final String str = alllines(br);

                handler.post(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        if (!is_command(str)) {
                            String[] con = get_mess(str);
                            String ip = con[0];
                            if (!ip.equals(""))
                                UserMessages.add_message(ip, con[1]);
                            else
                                UserMessages.add_message("Ip : Unknown", con[1]);
                        }else {
                            commandHandler = new CommandHandler(get_command(str));
                        }
                    }
                });
            }
        } catch (IOException e) {}
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



    public boolean is_command(String s){

        if (s.length()>=3 && s.charAt(0)=='*' && s.charAt(1)=='*' && s.charAt(2)=='*')
            return true;
        else return false;
    }
    public String get_command(String s){
        return s.substring(3);
    }

    public String[] get_mess(String s){
        int i=0;
        String[] ret = new String[2];
        while (!s.substring(i,i+4).equals("////")){
            i++;
        }

        ret[0]=getNameByIp(s.substring(i+4));
        ret[1]=s.substring(0,i-1);

        return ret;
    }

    public String getNameByIp(String ip){
        int i = MainActivity.devices.indexOf(ip);
        if (i>=0){
            return MainActivity.devices.get(i+1);
        }else return MainActivity.context.getResources().getString(R.string.unknowndev);
    }
}
