package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.data.utils.DateUtils;

public class Exam extends TokenRequest implements Serializable {
    @SerializedName("examId")
    private Integer id;

    @SerializedName("date")
    private Long date;

    @SerializedName("region")
    private String region;

    @SerializedName("members")
    private Integer members;

    public Exam(String region) {
        this.region = region;
    }

    public Exam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return DateUtils.millisToPattern(date, DateUtils.PATTERN_DATE);
    }
}