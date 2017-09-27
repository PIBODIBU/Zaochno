package ru.zaochno.zaochno.data.model.response;

import com.google.gson.annotations.SerializedName;

public class DataResponseWrapper<T> {
    @SerializedName("data")
    private T responseObj;

    public DataResponseWrapper(T responseObj) {
        this.responseObj = responseObj;
    }

    public T getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(T responseObj) {
        this.responseObj = responseObj;
    }
}