package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.annotation.Restrict;
import ru.zaochno.zaochno.data.annotation.processor.RestrictProcessor;
import ru.zaochno.zaochno.data.enums.UserAuthLevel;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.provider.AuthProvider;

public class DebugActivity extends AppCompatActivity {
    @BindView(R.id.tv_greet)
    public TextView tvGreet;

    @BindView(R.id.btn_sign_in)
    public Button btnSignIn;

    @BindView(R.id.btn_sign_out)
    public Button btnSignOut;

    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ButterKnife.bind(this);

        user = AuthProvider.getInstance(this).getCurrentUser();

        RestrictProcessor.bind(this);
    }

    @Restrict(userAuthLevel = UserAuthLevel.ANONYMOUS)
    public void onAnonymous() {
        tvGreet.setText("Вы не авторизованы");

        btnSignIn.setText("Войти");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DebugActivity.this, DebugActivityLogin.class));
                finish();
            }
        });

        btnSignOut.setVisibility(View.GONE);
    }

    @Restrict(userAuthLevel = UserAuthLevel.LOGGED)
    public void onSignedIn() {
        tvGreet.setText("Здравствуйте, ".concat(user.getName()));

        btnSignIn.setText("Продолжить");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DebugActivity.this, DebugActivityLogin.class));
                finish();
            }
        });

        btnSignOut.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_sign_out)
    public void signOut() {
        AuthProvider.getInstance(this).logOut();
        startActivity(new Intent(DebugActivity.this, DebugActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
