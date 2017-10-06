package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Region implements Serializable {
    @SerializedName("regionId")
    private Integer id;

    @SerializedName("resionName")
    private String name;

    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return getName();
    }
}
