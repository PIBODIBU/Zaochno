package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import ru.zaochno.zaochno.data.filter.TrainingListFilter;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.callback.TrainingActionListener;
import ru.zaochno.zaochno.ui.holder.BaseTrainingListViewHolder;

public abstract class BaseTrainingListAdapter<T extends BaseTrainingListViewHolder> extends RecyclerView.Adapter<T> {
    private final String TAG = "BaseTrainingListAdapter";

    protected List<Training> trainings;
    protected Context context;
    protected OnItemClickListener onItemClickListener;
    protected TrainingActionListener actionListener;

    public BaseTrainingListAdapter(Context context, TrainingActionListener actionListener) {
        this.context = context;
        this.trainings = new LinkedList<>();
        this.actionListener = actionListener;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        final Training training = trainings.get(position);

        if (training == null)
            return;

        if (training.getImgUrl() != null && training.getImgUrl() != "")
            Picasso.with(context)
                    .load(training.getImgUrl())
                    .into(holder.ivImg);

        if (training.getName() != null)
            holder.tvTitle.setText(training.getName());

        if (training.getShortText() != null)
            holder.tvDescription.setText(training.getShortText());

        if (onItemClickListener != null)
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(training);
                }
            });
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    public void filterByName(String name) {
        setTrainings(TrainingListFilter.byName(trainings, name));
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Training training);
    }

    public void setActionListener(TrainingActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
