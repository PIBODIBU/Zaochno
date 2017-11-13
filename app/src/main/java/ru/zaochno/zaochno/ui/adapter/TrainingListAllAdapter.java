package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.callback.TrainingActionListener;
import ru.zaochno.zaochno.ui.holder.TrainingListAllViewHolder;

public class TrainingListAllAdapter extends BaseTrainingListAdapter<TrainingListAllViewHolder> {
    private final String TAG = "TrainingListAllAdapter";

    public TrainingListAllAdapter(Context context, TrainingActionListener actionListener) {
        super(context, actionListener);
    }

    @Override
    public TrainingListAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListAllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListAllViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Training training = trainings.get(position);

        if (training == null)
            return;

        if (training.getLowestPrice() != null && training.getLowestPrice().getPrice() != null)
            holder.tvPrice.setText(String.valueOf(training.getLowestPrice().getPrice()));

        if (training.getFavourite())
            holder.ivFavourite.setImageResource(R.drawable.ic_star_white_24dp);
        else
            holder.ivFavourite.setImageResource(R.drawable.ic_favourite_white);

        holder.containerToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null)
                    actionListener.onFavourite(training);
            }
        });

        holder.containerBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null)
                    actionListener.onBuy(training);
            }
        });

        holder.containerDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null)
                    actionListener.onDemo(training);
            }
        });
    }
}