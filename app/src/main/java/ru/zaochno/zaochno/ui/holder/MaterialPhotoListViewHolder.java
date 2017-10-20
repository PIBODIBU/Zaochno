package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class MaterialPhotoListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view)
    public ImageView imageView;

    public MaterialPhotoListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
