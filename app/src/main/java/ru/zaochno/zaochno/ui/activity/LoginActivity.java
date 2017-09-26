package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.AuthResponse;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;

public class LoginActivity extends AppCompatActivity {
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

    @OnClick(R.id.btn_login)
    public void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // TODO temp code
        new SharedPrefUtils(this).setCurrentUser(new User(
                "John Smith",
                "email@gmail.com",
                "https://cdn.pixabay.com/photo/2016/08/20/05/38/avatar-1606916_960_720.png"
        ));
        startActivity(new Intent(this, TrainingListActivity.class));
        finish();

        /*Retrofit2Client.getInstance().getApi().authenticate(username, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_LONG).show();
            }
        });*/
    }
}