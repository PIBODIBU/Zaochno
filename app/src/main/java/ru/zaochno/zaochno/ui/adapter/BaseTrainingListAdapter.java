package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import ru.zaochno.zaochno.data.filter.TrainingListFilter;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.holder.BaseTrainingListViewHolder;

public abstract class BaseTrainingListAdapter<T extends BaseTrainingListViewHolder> extends RecyclerView.Adapter<T> {
    private final String TAG = "BaseTrainingListAdapter";

    protected List<Training> trainings;

    protected Context context;

    public BaseTrainingListAdapter(Context context) {
        this.context = context;
        this.trainings = new LinkedList<>();
    }

    public BaseTrainingListAdapter(LinkedList<Training> trainings, Context context) {
        this.trainings = trainings;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        Training training = trainings.get(position);

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
}
