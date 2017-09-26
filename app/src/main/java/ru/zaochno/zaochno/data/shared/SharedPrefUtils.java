package ru.zaochno.zaochno.data.shared;

import android.content.Context;
import android.content.SharedPreferences;

import ru.zaochno.zaochno.data.model.User;

public class SharedPrefUtils {
    private SharedPreferences sharedPreferences;

    public SharedPrefUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    }

    public void setCurrentUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user_name", user.getName());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_photo_url", user.getPhotoUrl());

        editor.apply();
    }

    public User getCurrentUser() {
        return new User(
                sharedPreferences.getString("user_name", ""),
                sharedPreferences.getString("user_email", ""),
                sharedPreferences.getString("user_photo_url", "")
        );
    }
}
