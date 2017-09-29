package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.AuthErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.btn_forgot_pass)
    public Button btnForgotPass;

    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    @BindView(R.id.ed_username)
    public EditText etUsername;

    @BindView(R.id.ed_password)
    public EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupUi();
    }

    private void setupUi() {
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivLogo);

        btnForgotPass.setPaintFlags(btnForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btn_skip)
    public void skip() {
        startActivity(new Intent(this, TrainingListActivity.class));
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (!isFieldValid(username) || !isFieldValid(password)) {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG).show();
            return;
        }

        Retrofit2Client.getInstance().getApi().authenticate(new User(username, password)).enqueue(new Callback<AuthErrorResponse>() {
            @Override
            public void onResponse(Call<AuthErrorResponse> call, Response<AuthErrorResponse> response) {
                if (response == null || response.body() == null || response.body().getToken() == null) {
                    Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_LONG).show();
                    return;
                }

                Retrofit2Client.getInstance().getApi().getUserInfo(new Token(response.body().getToken())).enqueue(new Callback<DataResponseWrapper<User>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                        if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                            Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_LONG).show();
                            return;
                        }

                        new SharedPrefUtils(LoginActivity.this).setCurrentUser(response.body().getResponseObj());

                        startActivity(new Intent(LoginActivity.this, TrainingListActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<AuthErrorResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Boolean isFieldValid(String field) {
        if (TextUtils.isEmpty(field))
            return false;
        return true;
    }
}