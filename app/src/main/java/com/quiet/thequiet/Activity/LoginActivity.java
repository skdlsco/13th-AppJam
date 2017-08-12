package com.quiet.thequiet.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quiet.thequiet.APIRequest;
import com.quiet.thequiet.Fonts;
import com.quiet.thequiet.R;
import com.quiet.thequiet.User;
import com.quiet.thequiet.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIRequest apiRequest = retrofit.create(APIRequest.class);
        dataBinding.loginBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBinding.loginEditId.getText().toString().equals("")) {
                    Toast.makeText(mContext, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dataBinding.loginEditPw.getText().toString().equals("")) {
                    Toast.makeText(mContext, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                apiRequest.Login(dataBinding.loginEditId.getText().toString(), dataBinding.loginEditPw.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("Asdfasdf", "" + response.body().toString());
                        Log.e("www", "" + response.body().getSuccess());
                        if (response.body().getSuccess()) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void setTexts() {
        dataBinding.loginTextTitle1.setTypeface(fonts.NanumBarunGothicBold);
        dataBinding.loginTextTitle2.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginBtnSubmit.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginEditId.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginEditPw.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginTextPw.setTypeface(fonts.NanumBarunGothic);
        dataBinding.loginToolBarTitle.setTypeface(fonts.NanumBarunGothicBold);

        dataBinding.loginEditId.setHint(Html.fromHtml("<i>아이디 입력</i>"));
        dataBinding.loginEditPw.setHint(Html.fromHtml("<i>비밀번호 입력</i>"));
        dataBinding.loginTextPw.setText(Html.fromHtml("<i>비밀번호를 잊으셨나요? <u>비밀번호 재설정</u></i>"));
    }
}
