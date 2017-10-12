package ru.zaochno.zaochno.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.activity.ExamNewActivity;
import ru.zaochno.zaochno.ui.activity.TrainingInfoActivity;
import ru.zaochno.zaochno.ui.adapter.TrainingListPayedAdapter;

public class TrainingListPayedFragment extends BaseTrainingListFragment<TrainingListPayedAdapter> {
    public TrainingListPayedFragment() {
        this.layoutId = R.layout.fragment_training_list_payed;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getAdapter().setPayedActionListener(new TrainingListPayedAdapter.PayedActionListener() {
            @Override
            public void onExam(Training training) {
                startActivity(new Intent(getActivity(), ExamNewActivity.class)
                        .putExtra(ExamNewActivity.INTENT_KEY_TRAINING_MODEL, training));
            }
        });

        return view;
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
