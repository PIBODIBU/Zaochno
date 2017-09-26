package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("code")
    private Integer code;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
