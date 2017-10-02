package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class BaseTrainingListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_img)
    public ImageView ivImg;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    @BindView(R.id.root_view)
    public View rootView;

    public BaseTrainingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
