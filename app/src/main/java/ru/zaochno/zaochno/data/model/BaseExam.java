package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ru.zaochno.zaochno.data.model.request.TokenRequest;

public abstract class BaseExam extends TokenRequest implements Serializable {
    @SerializedName("examId")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
