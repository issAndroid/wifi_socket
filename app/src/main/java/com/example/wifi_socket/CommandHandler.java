package com.example.wifi_socket;

import android.widget.Toast;

public class CommandHandler {
    String command;
    public static String gettedname="";
    static int indesx;

    public CommandHandler(String command) {
        this.command = command;
        run();
    }

    private void run() {
        if (command.substring(0,7).equals("getname")) {
            getname_command(command.substring(10));
        }else if (command.substring(0,6).equals("myname")){
            String p = command.substring(7);
            MainActivity.target_name=p;
            gettedname=p;
            ScanPage.list.get(indesx).set_name_dev(p);

            MainActivity.devices.add(ScanPage.list.get(indesx).ippp.getText().toString());
            MainActivity.devices.add(ScanPage.list.get(indesx).dev.getText().toString());

            ScanPage.grid_dev.invalidate();
        }else if (command.substring(0,4).equals("file")){
            res_file(command.substring(6));
        }
    }

    private void res_file(String substring) {
        int i=0;
        while (substring.charAt(i) != '*'){i++;}

        String file_name = substring.substring(0,i);

        int j=i+1;
        while (substring.charAt(j) != '*'){j++;}

        int size = Integer.valueOf(substring.substring(i+1,j));
        String ip = substring.substring(j+1);

        // pass to class,...
    }


    private void getname_command(String s) {

        int i=0;
        for (i=0;i<s.length();i++){
            if (s.charAt(i)=='*')
                break;
        }

        String ip =s.substring(0,i);
        String port = s.substring(i+3);

        MessSend m = new MessSend();
        m.execute("false","***myname="+Settings.my_name,ip,port,"true");
    }

    public static void get_target_name(String ip , String port,String tar_ip,int i) {
        indesx=i;
        MessSend m = new MessSend();
        m.execute("false","***getname"+"***"+ip+"***"+port,tar_ip,port,"true");
    }
}
