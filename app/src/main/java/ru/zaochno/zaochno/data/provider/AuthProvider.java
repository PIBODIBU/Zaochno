package ru.zaochno.zaochno.data.provider;

import android.content.Context;
import android.support.annotation.Nullable;

import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;

public class AuthProvider {
    private static AuthProvider instance;
    private static SharedPrefUtils sharedPrefUtils;

    private AuthProvider(Context context) {
        sharedPrefUtils = new SharedPrefUtils(context);
    }

    public static AuthProvider getInstance(Context context) {
        if (instance == null)
            instance = new AuthProvider(context);
        return instance;
    }

    public Boolean isAuthenticated() {
        return sharedPrefUtils.isLogged();
    }

    public void logOut() {
        sharedPrefUtils.setCurrentUser(null);
    }

    @Nullable
    public User getCurrentUser() {
        return sharedPrefUtils.getCurrentUser();
    }
}
