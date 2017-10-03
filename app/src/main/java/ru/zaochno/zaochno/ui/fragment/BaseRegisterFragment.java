package ru.zaochno.zaochno.ui.fragment;

import android.support.v4.app.Fragment;

import ru.zaochno.zaochno.data.model.User;

public abstract class BaseRegisterFragment extends Fragment {
    protected RegisterActionListener registerActionListener;

    public void setRegisterActionListener(RegisterActionListener registerActionListener) {
        this.registerActionListener = registerActionListener;
    }

    public interface RegisterActionListener {
        void onRegister(User user);
    }
}