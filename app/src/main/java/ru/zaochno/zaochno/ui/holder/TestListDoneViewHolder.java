package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TestListDoneViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    public TestListDoneViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
