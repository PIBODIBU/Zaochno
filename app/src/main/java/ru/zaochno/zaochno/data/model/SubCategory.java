package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

public class SubCategory {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Integer id;

    public SubCategory(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
