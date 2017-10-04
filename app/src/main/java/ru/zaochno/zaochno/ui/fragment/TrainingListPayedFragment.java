package ru.zaochno.zaochno.ui.fragment;

import org.greenrobot.eventbus.Subscribe;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.adapter.TrainingListPayedAdapter;

public class TrainingListPayedFragment extends BaseTrainingListFragment<TrainingListPayedAdapter> {
    public TrainingListPayedFragment() {
        this.layoutId = R.layout.fragment_training_list_payed;
    }

    @Subscribe
    public void onTrainingFavouriteEvent(TrainingFavouriteEvent event) {
        for (Training training : getAdapter().getTrainings())
            if (event.getTraining().getId() == training.getId()) {
                getAdapter().getTrainings().set(getAdapter().getTrainings().indexOf(training), training);
                break;
            }

        getAdapter().notifyDataSetChanged();
    }
}
