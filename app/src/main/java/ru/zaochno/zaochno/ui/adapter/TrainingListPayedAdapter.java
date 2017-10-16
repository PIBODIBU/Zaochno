package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.utils.DateUtils;
import ru.zaochno.zaochno.ui.callback.TrainingActionListener;
import ru.zaochno.zaochno.ui.holder.TrainingListPayedViewHolder;

public class TrainingListPayedAdapter extends BaseTrainingListAdapter<TrainingListPayedViewHolder> {
    private PayedActionListener payedActionListener;

    public TrainingListPayedAdapter(Context context, TrainingActionListener actionListener) {
        super(context, actionListener);
    }

    @Override
    public TrainingListPayedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainingListPayedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training_payed, parent, false));
    }

    @Override
    public void onBindViewHolder(TrainingListPayedViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Training training = trainings.get(position);

        if (training == null)
            return;

        if (training.getProgress() != null) {
            holder.tvProgress.setText(String.valueOf(training.getProgress()).concat("%"));
            holder.progressBar.setProgress((training.getProgress().intValue()));
        }

        if (training.getValidity() != null)
            holder.tvValidity.setText("Срок годности тренинга до ".concat(DateUtils.millisToPattern(training.getValidity(), DateUtils.PATTERN_DEFAULT)));

        if (payedActionListener != null)
            holder.containerExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payedActionListener.onExam(training);
                }
            });
    }

    public void setPayedActionListener(PayedActionListener payedActionListener) {
        this.payedActionListener = payedActionListener;
    }

    public interface PayedActionListener {
        void onExam(Training training);
    }
}