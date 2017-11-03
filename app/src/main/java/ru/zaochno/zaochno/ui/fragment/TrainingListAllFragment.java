package ru.zaochno.zaochno.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.DataRefreshFinished;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.adapter.TrainingListAllAdapter;

public class TrainingListAllFragment extends BaseTrainingListFragment<TrainingListAllAdapter> {
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public TrainingListAllFragment() {
        this.layoutId = R.layout.fragment_training_list_all;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        if (onRefreshListener != null)
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        return v;
    }

    @Subscribe
    public void onDataRefreshFinishedEvent(DataRefreshFinished event) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
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

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}