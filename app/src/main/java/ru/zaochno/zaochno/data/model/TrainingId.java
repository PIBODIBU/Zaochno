package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import ru.zaochno.zaochno.data.model.request.TokenRequest;

public class TrainingId extends TokenRequest {
    @SerializedName("trenningId")
    private Integer id;

    public TrainingId(String token) {
        super(token);
    }

    public TrainingId(String token, Integer id) {
        super(token);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
