package ru.zaochno.zaochno.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class MessageListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.container_content)
    public View containerContent;

    @BindView(R.id.tv_status)
    public TextView tvStatus;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_date)
    public TextView tvDate;

    @BindView(R.id.tv_text)
    public TextView tvText;

    @BindView(R.id.btn_cancel)
    public ImageButton ibCancel;

    public MessageListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
