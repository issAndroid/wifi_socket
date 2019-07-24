package com.example.wifi_socket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ArrayList list;
    GridView setting_list;
    ColorPicker colorPicker;
    ArrayList<String> list_of_colors;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        activity=SettingsActivity.this;
        setting_list=(GridView)findViewById(R.id.settings_list);
        list=new ArrayList();
        prepare_list();
        prepareMainList();
        setting_list.setAdapter(new MyGraidViewAdapter(list));
    }



    private void prepareMainList() {
        list.add(new SettingStringView(getApplicationContext()));

        for (String s : list_of_colors){
            SettingColorView colorView = new SettingColorView(getApplicationContext());
            colorView.setlastvalue(get_persian_name(s),Settings.getColor(s),s);
            list.add(colorView);
        }
    }


    private void prepare_list() {
        list_of_colors=new ArrayList<>();

        list_of_colors.add("send_me");
        list_of_colors.add("send_accepted");
        list_of_colors.add("send_error");
        list_of_colors.add("text_color1");
        list_of_colors.add("text_color2");
        list_of_colors.add("file_text_color1");
        list_of_colors.add("file_text_color2");
        list_of_colors.add("file_send_me");
        list_of_colors.add("file_send_accepted");
        list_of_colors.add("file_send_error");
        list_of_colors.add("colorAccent");
        list_of_colors.add("error_color");
        list_of_colors.add("bottomsheetcolor");
        list_of_colors.add("background_color");
        list_of_colors.add("scanpage_background_color");
    }

    public String get_persian_name(String s){
        if (s.equals("send_me"))
            return "پس زمینه پیام ارسال شده (خودم)";
        else if (s.equals("send_accepted"))
            return "پس زمینه پیام (دریافت شده) ";
        else if (s.equals("send_error"))
            return "پس زمینه خطا در ارسال پیام";
        else if (s.equals("text_color1"))
            return "عنوان پیام";
        else if (s.equals("text_color2"))
            return "متن پیام";
        else if (s.equals("file_text_color1"))
            return "عنوان فایل ارسال شده";
        else if (s.equals("file_text_color2"))
            return "توضیحات فایل ارسال شده";
        else if (s.equals("file_send_me"))
            return "پس زمینه فایل ارسالی (خودم)";
        else if (s.equals("file_send_accepted"))
            return "پس زمینه فایل (دریافت شده)";
        else if (s.equals("file_send_error"))
            return "پس زمینه خطا در ارسال فایل";
        else if (s.equals("colorAccent"))
            return "رنگ اصلی";
        else if (s.equals("error_color"))
            return "رنگ خطا";
        else if (s.equals("bottomsheetcolor"))
            return "رنگ کشو پایینی (bottom sheet)";
        else if (s.equals("background_color"))
            return "پس زمینه چت";
        else if (s.equals("scanpage_background_color"))
            return "پس زمینه صفحه اسکن";
        else return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(activity, "تغییرات در ورود بعدی اعمال می شوند", Toast.LENGTH_LONG).show();
    }
}
