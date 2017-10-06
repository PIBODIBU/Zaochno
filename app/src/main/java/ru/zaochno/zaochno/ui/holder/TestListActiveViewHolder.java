package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class TestListActiveViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_progress)
    public TextView tvProgress;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    @BindView(R.id.tv_validity)
    public TextView tvValidity;

    @BindView(R.id.btn_continue)
    public Button btnContinue;

    public TestListActiveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
