package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rey.material.widget.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class MaterialPhotoListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view)
    public ImageView imageView;

    @BindView(R.id.progress_bar)
    public ProgressView progressView;

    public MaterialPhotoListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
