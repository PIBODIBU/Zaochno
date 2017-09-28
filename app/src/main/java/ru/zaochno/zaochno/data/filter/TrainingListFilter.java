package ru.zaochno.zaochno.data.filter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import ru.zaochno.zaochno.data.model.Training;

public class TrainingListFilter {
    private static final String TAG = "TrainingListFilter";

    public static List<Training> byName(@NonNull List<Training> trainings, String name) {
        if (name == null || TextUtils.isEmpty(name))
            return trainings;

        List<Training> filtered = new LinkedList<>();

        for (Training training : trainings) {
            if (training.getName().toLowerCase().contains(name.toLowerCase()))
                filtered.add(training);
        }

        return filtered;
    }
}