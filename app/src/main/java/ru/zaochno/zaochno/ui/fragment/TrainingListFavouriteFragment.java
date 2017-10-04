package ru.zaochno.zaochno.ui.fragment;

import org.greenrobot.eventbus.Subscribe;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.adapter.TrainingListFavouriteAdapter;

public class TrainingListFavouriteFragment extends BaseTrainingListFragment<TrainingListFavouriteAdapter> {
    public TrainingListFavouriteFragment() {
        this.layoutId = R.layout.fragment_training_list_favourite;
    }

    @Subscribe
    public void onTrainingFavouriteEvent(TrainingFavouriteEvent event) {
        Boolean found = false;
        Boolean newStatus = event.getTraining().getFavourite();

        for (Training training : getAdapter().getTrainings())
            if (event.getTraining().getId() == training.getId()) {
                if (!newStatus) // Changed item now is not favourite
                    getAdapter().getTrainings().remove(training);

                found = true;
                break;
            }

        if (!found && newStatus)
            // Changed item is new and marked as favourite
            getAdapter().getTrainings().add(event.getTraining());

        getAdapter().notifyDataSetChanged();
    }
}
