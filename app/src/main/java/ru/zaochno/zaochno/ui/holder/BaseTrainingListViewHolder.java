package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public abstract class BaseTrainingListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_img)
    public ImageView ivImg;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    public BaseTrainingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
