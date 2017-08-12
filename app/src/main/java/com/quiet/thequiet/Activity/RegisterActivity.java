package com.quiet.thequiet.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quiet.thequiet.Fonts;
import com.quiet.thequiet.R;
import com.quiet.thequiet.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private Fonts fonts;
    private Context mContext = this;
    private ActivityRegisterBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fonts = new Fonts(mContext);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setFonts();
    }

    private void setFonts() {
        
    }
}
