package ru.zaochno.zaochno.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.AuthResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;

public interface IAPI {
    @POST("Account/Login")
    Call<AuthResponse> authenticate(@Body User user);

    @POST("Account/getUserProfile")
    Call<DataResponseWrapper<User>> getUserInfo(@Body Token token);
}