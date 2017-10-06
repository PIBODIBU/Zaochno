package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TrainingExamsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_time)
    public TextView tvTime;

    @BindView(R.id.tv_members)
    public TextView tvMembers;

    @BindView(R.id.btn_register)
    public Button btnRegister;

    public TrainingExamsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
