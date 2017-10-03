package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseErrorResponse {
    @SerializedName("registered")
    private Boolean registered;

    @SerializedName("token")
    private String token;

    public RegisterResponse(Boolean registered, String token) {
        this.registered = registered;
        this.token = token;
    }

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
}
