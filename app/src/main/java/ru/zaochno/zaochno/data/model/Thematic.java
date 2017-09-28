package ru.zaochno.zaochno.data.model;

public class Thematic {
    private String name;

    public Thematic(String name) {
        this.name = name;
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
