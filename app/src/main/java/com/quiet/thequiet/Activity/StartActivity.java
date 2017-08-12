package com.quiet.thequiet.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.quiet.thequiet.Fonts;
import com.quiet.thequiet.R;
import com.quiet.thequiet.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding dataBinding;
    private Fonts fonts;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mContext = this;
        fonts = new Fonts(this);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        setTexts();

        dataBinding.startBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });
        dataBinding.startBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTexts() {
        dataBinding.startText1.setTypeface(fonts.CabinRegular);
        dataBinding.startText2.setTypeface(fonts.CabinRegular);
        dataBinding.startBtnLogin.setTypeface(fonts.NanumBarunGothic);
        dataBinding.startBtnRegister.setTypeface(fonts.NanumBarunGothic);

    }
}
