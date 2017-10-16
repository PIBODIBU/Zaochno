package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class SubChapterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.tv_sub_chapter)
    public TextView tvSubChapter;

    public SubChapterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
