package ru.zaochno.zaochno.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_forgot_pass)
    public Button btnForgotPass;

    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

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
}