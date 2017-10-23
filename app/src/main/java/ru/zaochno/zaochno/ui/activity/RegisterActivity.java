package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

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
import ru.zaochno.zaochno.data.model.response.RegisterResponse;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;
import ru.zaochno.zaochno.ui.fragment.BaseRegisterFragment;
import ru.zaochno.zaochno.ui.fragment.RegisterTypeLawFragment;
import ru.zaochno.zaochno.ui.fragment.RegisterTypePhysFragment;

public class RegisterActivity extends AppCompatActivity implements BaseRegisterFragment.RegisterActionListener {
    @BindView(R.id.btn_type_phys)
    public Button btnTypePhys;

    @BindView(R.id.btn_type_law)
    public Button btnTypeLaw;

    private RegisterTypePhysFragment physFragment = new RegisterTypePhysFragment();
    private RegisterTypeLawFragment lawFragment = new RegisterTypeLawFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setupUi();
    }

    @OnClick({R.id.btn_type_phys, R.id.btn_type_law})
    public void selectType(Button button) {
        switch (button.getId()) {
            case R.id.btn_type_phys:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, physFragment)
                        .commit();

                btnTypePhys.setBackgroundResource(R.color.colorButtonPrimary);
                btnTypeLaw.setBackgroundResource(R.color.colorButtonPrimaryLight);
                break;

            case R.id.btn_type_law:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, lawFragment)
                        .commit();

                btnTypeLaw.setBackgroundResource(R.color.colorButtonPrimary);
                btnTypePhys.setBackgroundResource(R.color.colorButtonPrimaryLight);
                break;
        }
    }

    private void setupUi() {
        physFragment.setRegisterActionListener(this);
        lawFragment.setRegisterActionListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, physFragment)
                .commit();
    }

    @Override
    public void onRegister(User user) {
        Retrofit2Client.getInstance().getApi().register(user).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response == null || response.body() == null || !response.body().getRegistered()) {
                    Toast.makeText(RegisterActivity.this, "На данный e-mail уже зарегистрирован аккаунт в системе", Toast.LENGTH_LONG).show();
                    return;
                }

                Retrofit2Client.getInstance().getApi().getUserInfo(new Token(response.body().getToken())).enqueue(new Callback<DataResponseWrapper<User>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                        if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                            Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        new SharedPrefUtils(RegisterActivity.this).setCurrentUser(response.body().getResponseObj());

                        startActivity(new Intent(RegisterActivity.this, TrainingListActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "На данный e-mail уже зарегистрирован аккаунт в системе", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
