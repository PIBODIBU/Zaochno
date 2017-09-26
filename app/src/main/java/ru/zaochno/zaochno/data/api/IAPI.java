package ru.zaochno.zaochno.data.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.zaochno.zaochno.data.model.response.AuthResponse;

public interface IAPI {
    @FormUrlEncoded
    @POST("Account/Login")
    Call<AuthResponse> authenticate(@Field("userNick") String username, @Field("password") String password);
}
