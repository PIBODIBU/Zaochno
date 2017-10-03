package ru.zaochno.zaochno.data.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.zaochno.zaochno.data.model.Message;
import ru.zaochno.zaochno.data.model.Thematic;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;
import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.data.model.response.AuthErrorResponse;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;

public interface IAPI {
    @POST("Account/Login")
    Call<AuthErrorResponse> authenticate(@Body User user);

    @POST("Account/getUserProfile")
    Call<DataResponseWrapper<User>> getUserInfo(@Body Token token);

    @POST("trennings")
    Call<DataResponseWrapper<List<Training>>> getTrainings(@Body TrainingFilter trainingFilter);

    @POST("thematics")
    Call<DataResponseWrapper<List<Thematic>>> getThematics();

    @POST("feedback")
    Call<DataResponseWrapper<List<Message>>> getMessages(@Body TokenRequest tokenRequest);

    @POST("sendMessage")
    Call<BaseErrorResponse> sendMessage(@Body Message message);

    @POST("feedback/delete")
    Call<BaseErrorResponse> deleteMessage(@Body Message message);

    @POST("feedback/read")
    Call<BaseErrorResponse> markMessageAsRead(@Body Message message);

    @POST("trennings/trening")
    Call<DataResponseWrapper<TrainingFull>> getFullTraining(@Body Training training);
}