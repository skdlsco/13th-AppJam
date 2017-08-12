package com.quiet.thequiet.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.quiet.thequiet.Fonts;
import com.quiet.thequiet.R;
import com.quiet.thequiet.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding dataBinding;
    private Fonts fonts;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        fonts = new Fonts(mContext);

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setTexts();

        setSupportActionBar(dataBinding.loginToolBar);
    }

    private void setTexts() {
        dataBinding.loginTextTitle1.setTypeface(fonts.NanumBarunGothicBold);
        dataBinding.loginTextTitle2.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginBtnSubmit.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginEditId.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginEditPw.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginTextPw.setTypeface(fonts.NanumBarunGothic);

        dataBinding.loginEditId.setHint(Html.fromHtml("<i>아이디 입력</i>"));
        dataBinding.loginEditPw.setHint(Html.fromHtml("<i>비밀번호 입력</i>"));
        dataBinding.loginTextPw.setText(Html.fromHtml("<i>비밀번호를 잊으셨나요? <u>비밀번호 재설정</u></i>"));
    }
}
