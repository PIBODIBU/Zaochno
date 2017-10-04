package ru.zaochno.zaochno.data.event;

import ru.zaochno.zaochno.data.model.Training;

public class TrainingFavouriteEvent {
    private Training training;

    public TrainingFavouriteEvent(Training training) {
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
