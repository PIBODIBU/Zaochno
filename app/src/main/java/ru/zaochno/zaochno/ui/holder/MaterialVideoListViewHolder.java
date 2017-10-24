package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class MaterialVideoListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail)
    public ImageView ivThumbnail;

    public MaterialVideoListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
