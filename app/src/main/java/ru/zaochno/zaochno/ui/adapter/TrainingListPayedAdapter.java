package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.ui.holder.BaseTrainingListViewHolder;

public class TrainingListPayedAdapter extends BaseTrainingListAdapter<BaseTrainingListViewHolder> {
    public TrainingListPayedAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseTrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseTrainingListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }
}
