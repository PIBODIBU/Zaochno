package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Integer id;

    @SerializedName("subcategories")
    private List<SubThematic> subCategories;

    public Category(String name, Integer id, List<SubThematic> subCategories) {
        this.name = name;
        this.id = id;
        this.subCategories = subCategories;
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

    public List<SubThematic> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubThematic> subCategories) {
        this.subCategories = subCategories;
    }
}
