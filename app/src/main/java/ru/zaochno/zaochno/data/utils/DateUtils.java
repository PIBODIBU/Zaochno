package ru.zaochno.zaochno.data.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String PATTERN_DEFAULT = "dd MMMM yyyy HH:mm";
    public static final Long HOUR = 60 * 60 * 1000L;

    public static String millisToPattern(Long millis, String pattern) {
        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
