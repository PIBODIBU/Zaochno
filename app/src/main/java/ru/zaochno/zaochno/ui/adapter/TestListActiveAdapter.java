package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.ui.holder.TestListActiveViewHolder;

public class TestListActiveAdapter extends RecyclerView.Adapter<TestListActiveViewHolder> {
    private List<Test> tests;
    private Context context;

    public TestListActiveAdapter(List<Test> tests, Context context) {
        this.tests = tests;
        this.context = context;
    }

    @Override
    public TestListActiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestListActiveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_active, parent, false));
    }

    @Override
    public void onBindViewHolder(TestListActiveViewHolder holder, int position) {
        Picasso.with(context)
                .load(R.drawable.example_training)
                .into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }
}