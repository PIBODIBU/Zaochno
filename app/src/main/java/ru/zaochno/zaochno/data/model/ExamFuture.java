package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExamFuture extends BaseExam implements Serializable {
    @SerializedName("date")
    private String date;

    @SerializedName("members")
    private Integer members;

    public ExamFuture() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }
}