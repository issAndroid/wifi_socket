package com.example.wifi_socket;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class SettingColorView extends RelativeLayout {
    RelativeLayout rootview;
    TextView title;
    Button btn_color;
    String rname;

    public SettingColorView(Context context) {
        super(context);
        init(context);
    }

    public SettingColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootview = (RelativeLayout) inflate(context, R.layout.setting_color_view, this);
        btn_color = (Button) rootview.findViewById(R.id.btn_color);
        title = (TextView) rootview.findViewById(R.id.titlecolor);
    }


    public void setlastvalue(String title, int color, String realname) {
        this.title.setText(title);
        this.btn_color.setBackgroundColor(color);
        rname = realname;
        pickcolor();
    }


    public void pickcolor() {
        this.btn_color.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(SettingsActivity.activity, 0, 0, 0);
                colorPicker.enableAutoClose();
                colorPicker.show();
                colorPicker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        Settings.setColor(rname, color);
                        MainActivity.themeColor = new Settings();
                        btn_color.setBackgroundColor(color);
                    }
                });
            }
        });
    }
}
