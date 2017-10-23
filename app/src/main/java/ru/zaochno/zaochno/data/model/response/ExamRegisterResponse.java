package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class ExamRegisterResponse {
    @SerializedName("registered")
    private Boolean registered;

    @SerializedName("code")
    private Integer code;

    public ExamRegisterResponse(Boolean registered, Integer code) {
        this.registered = registered;
        this.code = code;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
