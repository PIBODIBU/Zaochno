package ru.zaochno.zaochno.data.api;

public class API {
    private static final API ourInstance = new API();

    public static API getInstance() {
        return ourInstance;
    }

    private API() {
    }
}
