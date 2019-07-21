package com.example.wifi_socket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FileView extends LinearLayout {
    LinearLayout rootview;
    TextView sender_ip, filename;
    public FileView(Context context) {
        super(context);
        init(context);
    }

    public FileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootview= (LinearLayout) inflate(context,R.layout.file_view,this);
        sender_ip=rootview.findViewById(R.id.sender_ip);
        filename=rootview.findViewById(R.id.filename);
    }

    public void setTextView(String ip, String filename) {
        this.sender_ip.setText(ip);
        this.filename.setText(filename);
    }

    public void set_text_color(int color1, int color2){
        this.filename.setTextColor(color2);
        this.sender_ip.setTextColor(color1);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void set_Color(int color, boolean b) {
        LinearLayout layout = rootview.findViewById(R.id.rootview2);
        if (b)
            layout.setGravity(Gravity.RIGHT);
        else layout.setGravity(Gravity.LEFT);

        layout.setBackground(getDrawableWithRadius(color));
    }


      private Drawable getDrawableWithRadius(int color) {

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }
}
