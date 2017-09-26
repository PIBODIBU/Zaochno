package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

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
                    Thread.sleep(3000);
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
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
