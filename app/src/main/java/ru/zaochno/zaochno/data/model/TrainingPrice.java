package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrainingPrice implements Serializable {
    @SerializedName("price")
    private Integer price;

    @SerializedName("duration")
    private Integer duration;

    public TrainingPrice(Integer price, Integer duration) {
        this.price = price;
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
