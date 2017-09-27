package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.holder.TrainingListViewHolder;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListViewHolder> {
    private LinkedList<Training> trainings;
    private Context context;

    public TrainingListAdapter(LinkedList<Training> trainings, Context context) {
        this.trainings = trainings;
        this.context = context;
    }

    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {
        Training training = trainings.get(position);

        if (training == null)
            return;

        if (training.getImgUrl() != null && training.getImgUrl() != "")
            Picasso.with(context)
                    .load(training.getImgUrl())
                    .into(holder.ivImg);

        if (training.getLowestPrice() != null && training.getLowestPrice().getPrice() != null)
            holder.tvPrice.setText(String.valueOf(training.getLowestPrice().getPrice()));

        if (training.getName() != null)
            holder.tvTitle.setText(training.getName());

        if (training.getShortText() != null)
            holder.tvDescription.setText(training.getShortText());
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }
}
