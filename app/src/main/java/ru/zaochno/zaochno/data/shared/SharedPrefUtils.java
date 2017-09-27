package ru.zaochno.zaochno.data.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import ru.zaochno.zaochno.data.model.User;

public class SharedPrefUtils {
    private SharedPreferences sharedPreferences;

    public SharedPrefUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    }

    public void setCurrentUser(User user) {
        if (user == null) {
            deleteCurrentUser();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("user_is_logged", true);
        editor.putString("user_name", user.getName());
        editor.putString("user_type", user.getType());
        editor.putString("user_region", user.getRegion());
        editor.putString("user_phone", user.getPhotoUrl());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_photo_url", user.getPhotoUrl());
        editor.putString("user_token", user.getToken());

        editor.apply();
    }

    public Boolean isLogged() {
        return sharedPreferences.getBoolean("user_is_logged", false);
    }

    private void deleteCurrentUser() {
        sharedPreferences.edit().clear().apply();
    }

    @Nullable
    public User getCurrentUser() {
        if (!isLogged())
            return null;

        return new User(
                sharedPreferences.getString("user_name", ""),
                sharedPreferences.getString("user_type", ""),
                sharedPreferences.getString("user_region", ""),
                sharedPreferences.getString("user_phone", ""),
                sharedPreferences.getString("user_email", ""),
                sharedPreferences.getString("user_photo_url", ""),
                sharedPreferences.getString("user_token", "")
        );
    }
}
