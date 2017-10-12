package ru.zaochno.zaochno.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TrainingListFavouriteViewHolder extends BaseTrainingListViewHolder {
    @BindView(R.id.container_price)
    public View containerPrice;

    @BindView(R.id.tv_price)
    public TextView tvPrice;

    @BindView(R.id.container_to_favourite)
    public View containerToFavourite;

    @BindView(R.id.iv_to_favourite)
    public ImageView ivFavourite;

    @BindView(R.id.container_buy)
    public View containerBuy;

    @BindView(R.id.container_demo)
    public View containerDemo;

    public TrainingListFavouriteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
