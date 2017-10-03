package ru.zaochno.zaochno.ui.callback;

import ru.zaochno.zaochno.data.model.User;

public interface OnUserUpdateListener {
    void onAvatarUpdate();

    void onUserInfoUpdate(User user);
}