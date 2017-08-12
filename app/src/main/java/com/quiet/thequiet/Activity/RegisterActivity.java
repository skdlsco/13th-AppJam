package com.quiet.thequiet.Activity;

import android.content.Context;
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
import com.quiet.thequiet.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        setTexts();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIRequest apiRequest = retrofit.create(APIRequest.class);
        dataBinding.registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBinding.registerEditName.getText().toString().equals("")) {
                    Toast.makeText(mContext, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dataBinding.registerEditId.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Id를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dataBinding.registerEditPw.getText().toString().equals("")) {
                    Toast.makeText(mContext, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (!dataBinding.registerEditRepw.getText().toString().equals(dataBinding.registerEditPw.getText().toString())) {
                    Toast.makeText(mContext, "비밀번호와 비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                apiRequest.Register(dataBinding.registerEditName.getText().toString(),
                        dataBinding.registerEditId.getText().toString(),
                        dataBinding.registerEditPw.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("Asdfasdf", "adsf" + response.body().toString());
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (json.getBoolean("success")) {
                                Toast.makeText(mContext, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setTexts() {
        dataBinding.registerToolBarTitle.setTypeface(fonts.NanumBarunGothic);
        dataBinding.registerEditId.setTypeface(fonts.NanumBarunGothic);
        dataBinding.registerEditPw.setTypeface(fonts.NanumBarunGothic);
        dataBinding.registerEditRepw.setTypeface(fonts.NanumBarunGothic);
        dataBinding.registerEditName.setTypeface(fonts.NanumBarunGothic);
        dataBinding.registerSubmit.setTypeface(fonts.NanumBarunGothic);

        dataBinding.registerEditName.setHint(Html.fromHtml("<i>이름 입력</i>"));
        dataBinding.registerEditId.setHint(Html.fromHtml("<i>아이디 입력</i>"));
        dataBinding.registerEditPw.setHint(Html.fromHtml("<i>비밀번호 입력</i>"));
        dataBinding.registerEditRepw.setHint(Html.fromHtml("<i>비밀번호 확인 입력</i>"));
    }
}
