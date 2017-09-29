package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Thematic {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("subcats")
    private List<SubThematic> subThematics;

    public Thematic(String name) {
        this.name = name;
    }

    public Thematic(Integer id, String name, List<SubThematic> subThematics) {
        this.id = id;
        this.name = name;
        this.subThematics = subThematics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubThematic> getSubThematics() {
        return subThematics;
    }

    public void setSubThematics(List<SubThematic> subThematics) {
        this.subThematics = subThematics;
    }

    @Override
    public String toString() {
        return getName();
    }
}
