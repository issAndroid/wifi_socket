package com.example.wifi_socket;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class FileRec extends Thread implements Runnable {

    Handler handler = new Handler();
    static String fileneme, ip;
    ServerSocket ss; Socket s;
    InputStream is; OutputStream os;
    static int size,count;
    static String name = "";

    public void setValues(String fileneme, int size, String ip){
        this.size=size;
        this.ip=ip;
        this.fileneme=fileneme;
    }


    @Override
    public void run() {
        super.run();
        try {
            ss=new ServerSocket(Integer.valueOf(Settings.file_port));
            s=ss.accept();
            is=s.getInputStream();
            name = Environment.getExternalStorageDirectory().getPath()+"/WifiSocket/"+fileneme;
            os=new FileOutputStream(name);
            byte[] bytes = new byte[size];



//            while (t<5000) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Run your task here
//                        File file = new File(name);
////                        MainActivity.message.append(file.length() + "**");
////                        double d = (double) file.length()/size *100;
////                        MainActivity.progressBar.setProgress((int)d);
////                        MainActivity.message.append(file.length() + "/"+size+"\n");
//                        Log.w("filellllll",file.length()+"/"+size);
//                        t++;
//
////                        try {
////                            sleep(100);
////                        } catch (InterruptedException e) {}
//                    }
//                }, 200);
//            }



            new MyAs().execute();

            while ((count = is.read(bytes)) > 0) {
                os.write(bytes, 0, count);
            }

            os.close();
            s.close();
            ss.close();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    UserMessages.add_file(getNameByIp(ip),fileneme);
                }
            });

        } catch (IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UserMessages.add_file(MainActivity.context.getResources().getString(R.string.rec_error),fileneme);
                }
            });
        }
    }

    public String getNameByIp(String ip){
        int i = MainActivity.devices.indexOf(ip);
        if (i>=0){
            return MainActivity.devices.get(i+1);
        }else return ip;
    }

    class MyAs extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            while (true){
                int rec_file = (int) new File(name).length();
                double d = (double) rec_file/size*100;
                MainActivity.progressBar.setProgress((int)d);

                try {
                    sleep(100);
                } catch (Exception e) {}

                if (size==rec_file)
                    break;
            }

            return null;
        }
    }
}
