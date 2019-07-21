package com.example.wifi_socket;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.File;

public class ProgUpdater extends Thread {

    @Override
    public void run() {
        super.run();

        while (MainActivity.progressBar.getProgress() != 100) {

            MainActivity.progressBar.setProgress(MainActivity.progressBar.getProgress() + 10);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    File f = new File(FileRec.fileneme);
                    MainActivity.message.setText(f.length()+"");
                }
            });



            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}
