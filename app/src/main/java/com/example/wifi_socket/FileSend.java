package com.example.wifi_socket;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.example.wifi_socket.FileRec.count;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.sleep;


public class FileSend extends AsyncTask<String,Void,Void> {
    Handler handler = new Handler();
    Socket socket;
    static int length, r=0, m=0;


    @Override
    protected Void doInBackground(String... strings) {
        //do somethings
        final String path = strings[0];
        String ip = strings[1];
        final File file = new File(path);
        length =(int) file.length();

            try{
            socket = new Socket(ip, Integer.valueOf(Settings.file_port));
            byte[] bytes = new byte[length];
            InputStream in = new FileInputStream(path);
            OutputStream out = socket.getOutputStream();

            while ((count = in.read(bytes,0, length)) > -1) {
                r+=count;
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            socket.close();



            handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    UserMessages.add_file(MainActivity.context.getResources().getString(R.string.send_me),next_of_zero(path));

                }
            });
        } catch (final IOException e) {
            handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    UserMessages.add_file(MainActivity.context.getResources().getString(R.string.send_error),":(");
//                    UserMessages.add_file(Main);
                }
            });
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.my_sends.size()!=0)
                    MainActivity.send_file();
                else MainActivity.file.setClickable(true);
            }
        });
    }

    class MyAs extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            int o=0;
            while (o<50) {
                o++;
                double rec_file = (double) r/length*100;
                    MainActivity.progressBar.setProgress((int)rec_file);
                    Log.w("fileeeee",(int) rec_file+"***"+length+"///"+o);


                try {
                    sleep(100);
                } catch (InterruptedException e) {return null;}
            }

            return null;
        }
    }

    public String next_of_zero(String s){
        int t=0;
        while (s.charAt(t)!='0'){t++;}
        return s.substring(t+1);
    }
}
