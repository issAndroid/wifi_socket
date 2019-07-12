package com.example.wifi_socket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MessageView extends LinearLayout {

    LinearLayout rootView;
    TextView ip,message;


    public MessageView(Context context) {
        super(context);
        init(context);
    }


    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void setTextView(String ip, String message){
        this.ip.setText(ip);
        this.message.setText(message);
    }


    private void init(Context context) {
        rootView= (LinearLayout) inflate(context,R.layout.message_view_coustom,this);
        ip= rootView.findViewById(R.id.ip_view);
        message= rootView.findViewById(R.id.message_view);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void set_Color(int color, boolean gr){
        LinearLayout layout = this.rootView.findViewById(R.id.rootview);
        layout.setBackground(getDrawableWithRadius(color));
        if (gr)
            layout.setGravity(Gravity.RIGHT);
        else layout.setGravity(Gravity.LEFT);
    }


    private Drawable getDrawableWithRadius(int color) {

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 0, 0, 40, 40, 20, 20});
//        gradientDrawable.set
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

}
