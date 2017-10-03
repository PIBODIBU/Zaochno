package ru.zaochno.zaochno.data.enums;

public enum UserType {
    DEFAULT(""),
    PHYS("Физ. лицо"),
    LAW("Юр. лицо");

    private String rawType;

    UserType(String rawType) {
        this.rawType = rawType;
    }

    public String rawType() {
        return rawType;
    }
}
