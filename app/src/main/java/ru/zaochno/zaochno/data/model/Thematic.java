package ru.zaochno.zaochno.data.model;

import java.util.List;

public class Thematic {
    private Integer id;
    private String name;
    private List<SubCategory> subCategories;

    public Thematic(String name) {
        this.name = name;
    }

    public Thematic(Integer id, String name, List<SubCategory> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
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

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return getName();
    }
}
