package ru.zaochno.zaochno.ui.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TrainingListAllViewHolder extends BaseTrainingListViewHolder {
    @BindView(R.id.tv_price)
    public TextView tvPrice;

    public TrainingListAllViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
