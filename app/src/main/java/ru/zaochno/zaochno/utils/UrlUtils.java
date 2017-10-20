package ru.zaochno.zaochno.utils;

public class UrlUtils {
    public static String htmlPathToUrl(String absolutePath) {
        if (absolutePath == null)
            return "";

        return "http://zaochno.ru/".concat(absolutePath.replace("../", ""));
    }
}
