package ru.zaochno.zaochno.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.holder.TrainingListViewHolder;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListViewHolder> {
    private LinkedList<Training> trainings;

    public TrainingListAdapter(LinkedList<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }
}
