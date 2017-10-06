package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Exam extends BaseExam implements Serializable {
    @SerializedName("date")
    private Long date;

    @SerializedName("region")
    private String region;

    public Exam() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}