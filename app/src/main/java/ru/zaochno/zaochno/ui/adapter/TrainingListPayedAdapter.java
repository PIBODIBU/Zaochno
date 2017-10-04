package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.callback.TrainingActionListener;
import ru.zaochno.zaochno.ui.holder.TrainingListPayedViewHolder;

public class TrainingListPayedAdapter extends BaseTrainingListAdapter<TrainingListPayedViewHolder> {
    public TrainingListPayedAdapter(Context context, TrainingActionListener actionListener) {
        super(context, actionListener);
    }

    @Override
    public TrainingListPayedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListPayedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListPayedViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Training training = trainings.get(position);

        if (training == null)
            return;

        holder.containerPrice.setVisibility(View.GONE);

        if (this.actionListener != null) {
            holder.containerToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.onFavourite(training);
                }
            });

            holder.containerBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.onBuy(training);
                }
            });

            holder.containerDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionListener.onDemo(training);
                }
            });
        }
    }
}
