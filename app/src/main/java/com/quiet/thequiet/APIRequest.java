package com.quiet.thequiet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by eka on 2017. 8. 13..
 */

public interface APIRequest {
    String baseUrl = "http://soylatte.kr:3000";

    @FormUrlEncoded
    @POST("/auth/login")
    Call<User> Login(@Field("id") String UserId, @Field("password") String UserPassword);

    @FormUrlEncoded
    @POST("/auth/register")
    Call<ResponseBody> Register(@Field("username") String UserName, @Field("id") String UserId, @Field("password") String UserPassword);

    @POST("/place/main")
    Call<ResponseBody> getLocations();

}
