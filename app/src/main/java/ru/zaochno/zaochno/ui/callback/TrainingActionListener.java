package ru.zaochno.zaochno.ui.callback;

import ru.zaochno.zaochno.data.model.Training;

public interface TrainingActionListener {
    void onFavourite(Training training);

    void onDemo(Training training);

    void onBuy(Training training);
}
