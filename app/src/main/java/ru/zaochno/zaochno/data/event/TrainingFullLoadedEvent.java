package ru.zaochno.zaochno.data.event;

import ru.zaochno.zaochno.data.model.TrainingFull;

public class TrainingFullLoadedEvent {
    private TrainingFull trainingFull;

    public TrainingFullLoadedEvent(TrainingFull trainingFull) {
        this.trainingFull = trainingFull;
    }

    public TrainingFull getTrainingFull() {
        return trainingFull;
    }
}
