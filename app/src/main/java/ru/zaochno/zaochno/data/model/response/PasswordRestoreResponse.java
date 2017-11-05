package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class PasswordRestoreResponse extends BaseErrorResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
