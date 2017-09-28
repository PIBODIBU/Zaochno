package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.holder.TrainingListAllViewHolder;

public class TrainingListAllAdapter extends BaseTrainingListAdapter<TrainingListAllViewHolder> {
    public TrainingListAllAdapter(LinkedList<Training> trainings, Context context) {
        super(trainings, context);
    }

    @Override
    public TrainingListAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListAllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListAllViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Training training = trainings.get(position);

        if (training == null)
            return;

        if (training.getLowestPrice() != null && training.getLowestPrice().getPrice() != null)
            holder.tvPrice.setText(String.valueOf(training.getLowestPrice().getPrice()));
    }
}
