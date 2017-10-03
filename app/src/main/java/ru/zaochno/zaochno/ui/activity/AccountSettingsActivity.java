package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.enums.UserType;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;
import ru.zaochno.zaochno.ui.callback.OnUserUpdateListener;
import ru.zaochno.zaochno.ui.fragment.AccountSettingsLawFragment;
import ru.zaochno.zaochno.ui.fragment.AccountSettingsPhysFragment;

public class AccountSettingsActivity extends BaseNavDrawerActivity implements OnUserUpdateListener {
    private static final String TAG = "AccountSettingsActivity";

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    public User user;
    private AccountSettingsPhysFragment physFragment = new AccountSettingsPhysFragment();
    private AccountSettingsLawFragment lawFragment = new AccountSettingsLawFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("");
        setupDrawer();

        setupUi();
        fetchData();
    }

    private void setupFragments() {
        String userType = AuthProvider.getInstance(this).getCurrentUser().getType();

        physFragment.setUser(user);
        lawFragment.setUser(user);

        physFragment.setOnUserUpdateListener(this);
        lawFragment.setOnUserUpdateListener(this);

        if (userType.equals(UserType.PHYS.rawType())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, physFragment)
                    .commit();
        } else if (userType.equals(UserType.LAW.rawType())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, lawFragment)
                    .commit();
        }
    }

    private void fetchData() {
        Retrofit2Client.getInstance().getApi().getUserInfo(new Token(AuthProvider.getInstance(this).getCurrentUser().getToken())).enqueue(new Callback<DataResponseWrapper<User>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                    Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                AccountSettingsActivity.this.user = response.body().getResponseObj();
                setupFragments();
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupUi() {
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivToolbarLogo);
    }

    @Override
    public void onAvatarUpdate() {

    }

    @Override
    public void onUserInfoUpdate(User user) {
        Retrofit2Client.getInstance().getApi().updateUserInfo(user).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                if (response == null || response.body() == null || response.body().getError()) {
                    Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                Retrofit2Client.getInstance().getApi().getUserInfo(new Token(AuthProvider.getInstance(AccountSettingsActivity.this).getCurrentUser().getToken()))
                        .enqueue(new Callback<DataResponseWrapper<User>>() {
                            @Override
                            public void onResponse(Call<DataResponseWrapper<User>> call, Response<DataResponseWrapper<User>> response) {
                                if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                                    Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                    return;
                                }

                                new SharedPrefUtils(AccountSettingsActivity.this).setCurrentUser(response.body().getResponseObj());

                                startActivity(new Intent(AccountSettingsActivity.this, TrainingListActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(Call<DataResponseWrapper<User>> call, Throwable t) {
                                Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();

                            }
                        });
            }

            @Override
            public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                Toast.makeText(AccountSettingsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
