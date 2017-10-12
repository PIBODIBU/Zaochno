package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class AnswerListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_text)
    public TextView tvText;

    public AnswerListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
