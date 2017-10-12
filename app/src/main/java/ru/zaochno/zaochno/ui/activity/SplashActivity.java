package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";

    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    @BindView(R.id.iv_hello)
    public ImageView ivHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setupUi();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    if (AuthProvider.getInstance(SplashActivity.this).isAuthenticated())
                        Retrofit2Client.getInstance().getApi().getUserInfo(new Token(AuthProvider.getInstance(SplashActivity.this).getCurrentUser().getToken()))
                                .enqueue(new Callback<DataResponseWrapper<User>>() {
                                    @Override
                                    public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, TrainingListActivity.class));
                                        SplashActivity.this.finish();
                                    }

                                    @Override
                                    public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                                        AuthProvider.getInstance(SplashActivity.this).logOut();
                                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, TrainingListActivity.class));
                                        SplashActivity.this.finish();
                                    }
                                });
                    else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, TrainingListActivity.class));
                        SplashActivity.this.finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupUi() {
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivLogo);

        Picasso.with(this)
                .load(R.drawable.img_welcome)
                .into(ivHello);
    }
}
