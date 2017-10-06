package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.data.utils.DateUtils;
import ru.zaochno.zaochno.ui.holder.TestListDoneViewHolder;

public class TestListDoneAdapter extends RecyclerView.Adapter<TestListDoneViewHolder> {
    private List<Test> tests;
    private Context context;

    public TestListDoneAdapter(List<Test> tests, Context context) {
        this.tests = tests;
        this.context = context;
    }

    @Override
    public TestListDoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestListDoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_done, parent, false));
    }

    @Override
    public void onBindViewHolder(TestListDoneViewHolder holder, int position) {
        Test test = tests.get(position);

        if (test == null)
            return;

        Picasso.with(context)
                .load(test.getCoverUrl())
                .into(holder.ivCover);

        holder.tvTitle.setText(test.getName());
        holder.tvDescription.setText(test.getDescription());
        holder.tvValidity.setText("Срок годности тренинга до ".concat(DateUtils.millisToPattern(test.getTrainingValidity(), DateUtils.PATTERN_DEFAULT)));
        holder.tvProgress.setText(String.valueOf(test.getProgress()).concat("%"));
        holder.progressBar.setProgress(test.getProgress());
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }
}
