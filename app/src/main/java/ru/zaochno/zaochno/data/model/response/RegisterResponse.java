package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseErrorResponse {
    @SerializedName("registered")
    private Boolean registered;

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
