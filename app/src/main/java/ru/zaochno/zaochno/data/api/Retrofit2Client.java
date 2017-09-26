package ru.zaochno.zaochno.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zaochno.zaochno.BuildConfig;

public class Retrofit2Client {
    private static final String API_URL = "https://zaochno.ru/api/";
    private static final Retrofit2Client instance = new Retrofit2Client();

    private Retrofit retrofit;
    private OkHttpClient client;

    private IAPI api;

    private Retrofit2Client() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG)
            okHttpBuilder.addInterceptor(loggingInterceptor);

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(IAPI.class);
    }

    public IAPI getApi() {
        return api;
    }

    public static Retrofit2Client getInstance() {
        return instance;
    }
}
