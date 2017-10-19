package ru.zaochno.zaochno.ui.fragment;

import org.greenrobot.eventbus.Subscribe;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.adapter.TrainingListAllAdapter;

public class TrainingListAllFragment extends BaseTrainingListFragment<TrainingListAllAdapter> {
    public TrainingListAllFragment() {
        this.layoutId = R.layout.fragment_training_list_all;
    }

    @Subscribe
    public void onTrainingFavouriteEvent(TrainingFavouriteEvent event) {
        Boolean found = false;

        for (Training training : getAdapter().getTrainings())
            if (event.getTraining().getId() == training.getId()) {
                getAdapter().getTrainings().set(getAdapter().getTrainings().indexOf(training), training);
                found = true;
                break;
            }

        if (!found)
            getAdapter().getTrainings().add(event.getTraining());

        getAdapter().notifyDataSetChanged();
    }
}