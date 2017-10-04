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

    public BaseTrainingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
