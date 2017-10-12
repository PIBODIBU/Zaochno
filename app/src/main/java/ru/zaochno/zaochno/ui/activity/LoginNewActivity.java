package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.rey.material.widget.ImageButton;

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

public class LoginNewActivity extends AppCompatActivity {
    @BindView(R.id.coordinator)
    public CoordinatorLayout coordinatorLayout;

    @BindView(R.id.ib_menu)
    public ImageButton ibMenu;

    @BindView(R.id.til_login)
    public TextInputLayout tilLogin;

    @BindView(R.id.til_password)
    public TextInputLayout tilPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);

        setupUi();
    }

    private void setupUi() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(LoginNewActivity.this, ibMenu);
                popupMenu.inflate(R.menu.menu_debug);
                popupMenu.show();
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String username = tilLogin.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        Retrofit2Client.getInstance().getApi().authenticate(new User(username, password)).enqueue(new Callback<AuthErrorResponse>() {
            @Override
            public void onResponse(Call<AuthErrorResponse> call, Response<AuthErrorResponse> response) {
                if (response == null || response.body() == null || response.body().getToken() == null) {
                    Snackbar.make(coordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
                    return;
                }

                Retrofit2Client.getInstance().getApi().getUserInfo(new Token(response.body().getToken())).enqueue(new Callback<DataResponseWrapper<User>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                        if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                            Snackbar.make(coordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
                            return;
                        }

                        new SharedPrefUtils(LoginNewActivity.this).setCurrentUser(response.body().getResponseObj());

                        startActivity(new Intent(LoginNewActivity.this, SplashNewActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                        Snackbar.make(coordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<AuthErrorResponse> call, Throwable t) {
                Snackbar.make(coordinatorLayout, R.string.error, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
