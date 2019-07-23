package com.example.wifi_socket;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
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
            name = Environment.getExternalStorageDirectory().getPath()+"/WifiSocket/"+dir_by_type(fileneme)+"/"+fileneme;
            os=new FileOutputStream(name);
            byte[] bytes = new byte[size];
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
                    UserMessages.add_file(getNameByIp(ip),"/WifiSocket/"+dir_by_type(fileneme)+"/"+fileneme,true);
                }
            });

        } catch (IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UserMessages.add_file(MainActivity.context.getResources().getString(R.string.rec_error),fileneme,false);
                }
            });
        }
    }


    public String dir_by_type(String name){
        String format = get_format(name);
        format = format.toLowerCase();
        if (format.equals("jpg")||format.equals("jpeg")||format.equals("gif")||format.equals("bmp")||format.equals("png")||format.equals("svg")){
            return "Picture";
        }
        else if (format.equals("3gp")||format.equals("m4a")||format.equals("m4b")||format.equals("mp3")||format.equals("oog")){
            return "Music";
        }
        else if (format.equals("flv")||format.equals("ogg")||format.equals("avi")||format.equals("wmv")||format.equals("mp4")||format.equals("3gp")||format.equals("ts")){
            return "Video";
        }
        else if (format.equals("html")||format.equals("htm")||format.equals("css")||format.equals("pdf")||format.equals("docx")||format.equals("xml")||format.equals("doc")){
            return "Document";
        }
        else return "Other";
    }


    public String get_format(String s){
        int t = s.length()-1;
        while (s.charAt(t)!='.'){t--;}
        return s.substring(t+1);
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
