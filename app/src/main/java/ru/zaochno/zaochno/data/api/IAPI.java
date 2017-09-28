package ru.zaochno.zaochno.data.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.zaochno.zaochno.data.model.Message;
import ru.zaochno.zaochno.data.model.Thematic;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;
import ru.zaochno.zaochno.data.model.response.AuthResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;

public interface IAPI {
    @POST("Account/Login")
    Call<AuthResponse> authenticate(@Body User user);

    @POST("Account/getUserProfile")
    Call<DataResponseWrapper<User>> getUserInfo(@Body Token token);

    @POST("trennings")
    Call<DataResponseWrapper<List<Training>>> getTrainings(@Body TrainingFilter trainingFilter);

    @POST("thematics")
    Call<DataResponseWrapper<List<Thematic>>> getThematics();

    @POST("feedback")
    Call<DataResponseWrapper<List<Message>>> getMessages(@Body TrainingFilter trainingFilter);
}