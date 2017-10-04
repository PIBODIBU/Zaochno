package ru.zaochno.zaochno.ui.callback;

import android.net.Uri;

import ru.zaochno.zaochno.data.model.User;

public interface OnUserUpdateListener {
    void onAvatarUpdate(Uri avatarUri);

    void onUserInfoUpdate(User user);
}