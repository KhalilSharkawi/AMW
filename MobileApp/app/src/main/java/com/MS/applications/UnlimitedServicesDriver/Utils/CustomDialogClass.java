package com.MS.applications.UnlimitedServicesDriver.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.R;

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    private String mType;
    public ImageView img;
    public Dialog d;
    public Button yes, no;
    public TextView area;
    public TextView total;
    public Button ok;
    public Button cancel;
    public TextView lat, lng;
    public EditText location;

    public CustomDialogClass(Activity a, String type) {
        super(a);
        this.c = a;
        this.mType = type;
    }

    public CustomDialogClass(Context context, String spinner) {
        super(context);
        this.c = (Activity) context;
        this.mType = spinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        switch (mType) {
            case "sure":
                setContentView(R.layout.custom_dialog);
                yes = findViewById(R.id.btn_yes);
                no = findViewById(R.id.btn_no);
                yes.setOnClickListener(this);
                no.setOnClickListener(this);
                break;
            case "spinner":
                setContentView(R.layout.custom_spinner_yellow);
                break;
            case "log_out":
                setContentView(R.layout.log_out_dialog);
                yes = findViewById(R.id.btn_yes);
                no = findViewById(R.id.btn_no);
                yes.setOnClickListener(this);
                no.setOnClickListener(this);
                break;
        }
    }
    @Override
    public void onClick(View v) { }
}
