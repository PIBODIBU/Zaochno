package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class AuthResponse extends BaseResponse {
    @SerializedName("data")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + ": " + token + "]";
    }
}
