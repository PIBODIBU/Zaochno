package ru.zaochno.zaochno.data.model.filter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.zaochno.zaochno.data.model.Thematic;

public class TrainingFilter {
    @SerializedName("take")
    private Integer number;

    @SerializedName("token")
    private String token;

    @SerializedName("thematics")
    private List<Thematic> thematics;

    @SerializedName("priceStart")
    private Integer priceStart;

    @SerializedName("priceEnd")
    private Integer priceEnd;

    public TrainingFilter() {
    }

    public TrainingFilter(Integer number, String token, List<Thematic> thematics, Integer priceStart, Integer priceEnd) {
        this.number = number;
        this.token = token;
        this.thematics = thematics;
        this.priceStart = priceStart;
        this.priceEnd = priceEnd;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Thematic> getThematics() {
        return thematics;
    }

    public void setThematics(List<Thematic> thematics) {
        this.thematics = thematics;
    }

    public Integer getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Integer priceStart) {
        this.priceStart = priceStart;
    }

    public Integer getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Integer priceEnd) {
        this.priceEnd = priceEnd;
    }
}
