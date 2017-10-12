package ru.zaochno.zaochno.ui.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TrainingListPayedViewHolder extends BaseTrainingListViewHolder {
    @BindView(R.id.tv_progress)
    public TextView tvProgress;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.container_exam)
    public View containerExam;

    @BindView(R.id.container_continue)
    public View containerContinue;

    @BindView(R.id.tv_validity)
    public TextView tvValidity;

    public TrainingListPayedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
