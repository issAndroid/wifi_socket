package com.example.wifi_socket;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SettingStringView extends LinearLayout {
    LinearLayout rootview;
    EditText myname,messageport,fileport;
    Button btn_default;
    public SettingStringView(Context context) {
        super(context);
        init(context);
    }
    public SettingStringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        rootview= (LinearLayout) inflate(context,R.layout.setting_text_view,this);
        myname= (EditText) rootview.findViewById(R.id.my_name);
        messageport= (EditText) rootview.findViewById(R.id.message_port);
        btn_default=(Button) findViewById(R.id.btn_default);
        fileport= (EditText) rootview.findViewById(R.id.file_port);
        //to set
        setportfile(fileport);
        setportmessage(messageport);
        setmyname(myname);
        // to get
        getlast();
        default_color();
    }
    private void getlast() {
        this.myname.setText(Settings.my_name);
        this.messageport.setText(Settings.mess_port);
        this.fileport.setText(Settings.file_port);
    }
    public void setportmessage(final EditText messageport){
        messageport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int mport = Integer.valueOf(messageport.getText().toString());
                    if (mport<1000 || mport>9999)
                        messageport.setError("معتبر نیست");
                    else Settings.setMess_port(String.valueOf(mport));
                }catch (Exception e){fileport.setError("معتبر نیست");}
            }
        });
    }
    public void setportfile(final EditText fileport){
        fileport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int fport = Integer.valueOf(fileport.getText().toString());
                    if (fport<1000 || fport>9999)
                        fileport.setError("معتبر نیست");
                    else Settings.setFile_port(String.valueOf(fport));
                }catch (Exception e){fileport.setError("معتبر نیست");}
            }
        });
    }
    public void setmyname(final EditText myname){
        myname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Settings.setMy_name(myname.getText().toString());
            }
        });
    }
    private void default_color() {
        btn_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.editor.clear();
                Settings.editor.apply();
                SettingsActivity.activity.finish();
            }
        });
    }
}
