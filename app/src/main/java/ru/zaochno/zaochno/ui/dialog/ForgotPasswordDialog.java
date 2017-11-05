package ru.zaochno.zaochno.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.response.PasswordRestoreResponse;
import ru.zaochno.zaochno.databinding.DialogForgotPasswordBinding;
import ru.zaochno.zaochno.ui.activity.MessageListActivity;

public class ForgotPasswordDialog extends DialogFragment {
    private final String TAG = "ForgotPasswordDialog";

    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_forgot_password, container, false);
        DialogForgotPasswordBinding binding = DialogForgotPasswordBinding.bind(rootView);
        ButterKnife.bind(this, rootView);

        binding.setUser(user);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
    }

    @OnClick(R.id.btn_done)
    public void restore() {
        Retrofit2Client.getInstance().getApi().restorePassword(user).enqueue(new Callback<PasswordRestoreResponse>() {
            @Override
            public void onResponse(Call<PasswordRestoreResponse> call, Response<PasswordRestoreResponse> response) {
                if (response == null || response.body() == null || response.body().getError() == null) {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                if (response.body().getError())
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getActivity(), "Пароль сброшен. Проверьте Вашу почту.", Toast.LENGTH_LONG).show();
                    getDialog().cancel();
                }
            }

            @Override
            public void onFailure(Call<PasswordRestoreResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
