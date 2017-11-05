package ru.zaochno.zaochno.data.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import ru.zaochno.zaochno.data.model.Exam;
import ru.zaochno.zaochno.data.model.Message;
import ru.zaochno.zaochno.data.model.Region;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.data.model.Thematic;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.TrainingId;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.UserAnswerSet;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;
import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.data.model.response.AuthErrorResponse;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.model.response.ExamRegisterResponse;
import ru.zaochno.zaochno.data.model.response.PasswordRestoreResponse;
import ru.zaochno.zaochno.data.model.response.RegisterResponse;

public interface IAPI {
    // Account
    @POST("Account/Login")
    Call<AuthErrorResponse> authenticate(@Body User user);

    @POST("register")
    Call<RegisterResponse> register(@Body User user);

    @POST("Account/Update")
    Call<BaseErrorResponse> updateUserInfo(@Body User user);

    @Multipart
    @POST("Account/Update/Avatar")
    Call<BaseErrorResponse> updateUserAvatar(@Part("token") RequestBody token, @Part MultipartBody.Part avatar);

    @POST("Account/getUserProfile")
    Call<DataResponseWrapper<User>> getUserInfo(@Body Token token);

    @POST("Account/Restore")
    Call<PasswordRestoreResponse> restorePassword(@Body User user);


    // Trainings
    @POST("trennings")
    Call<DataResponseWrapper<List<Training>>> getTrainings(@Body TrainingFilter trainingFilter);

    @POST("trennings/trening")
    Call<DataResponseWrapper<TrainingFull>> getFullTraining(@Body Training training);

    @POST("trennings/favorite")
    Call<BaseErrorResponse> favouriteTraining(@Body Training training);

    @POST("thematics")
    Call<DataResponseWrapper<List<Thematic>>> getThematics();


    // Messages
    @POST("feedback")
    Call<DataResponseWrapper<List<Message>>> getMessages(@Body TokenRequest tokenRequest);

    @POST("sendMessage")
    Call<BaseErrorResponse> sendMessage(@Body Message message);

    @POST("feedback/delete")
    Call<BaseErrorResponse> deleteMessage(@Body Message message);

    @POST("feedback/read")
    Call<BaseErrorResponse> markMessageAsRead(@Body Message message);


    // Tests
    @POST("tests/running")
    Call<DataResponseWrapper<List<Test>>> getActiveTests(@Body TrainingId token);

    @POST("tests/done")
    Call<DataResponseWrapper<List<Test>>> getDoneTests(@Body TrainingId token);

    @POST("tests/test")
    Call<DataResponseWrapper<Test>> getTest(@Body Test test);

    @POST("tests/test/register")
    Call<BaseErrorResponse> sendTestResult(@Body UserAnswerSet answerSet);


    // Exams
    @POST("exams/shedulle")
    Call<DataResponseWrapper<List<Exam>>> getTrainingExams(@Body Training training);

    @POST("shedulle/register")
    Call<ExamRegisterResponse> registerOnExam(@Body Exam exam);


    // Other
    @POST("regions")
    Call<DataResponseWrapper<List<Region>>> getRegions();
}