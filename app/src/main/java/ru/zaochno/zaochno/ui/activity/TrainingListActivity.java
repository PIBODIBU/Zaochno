package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.ui.adapter.TrainingListAdapter;

public class TrainingListActivity extends BaseNavDrawerActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private TrainingListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private LinkedList<Training> trainings = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("");
        setupDrawer();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        trainings.add(new Training());
        trainings.add(new Training());
        trainings.add(new Training());
        trainings.add(new Training());
        trainings.add(new Training());

        adapter = new TrainingListAdapter(trainings);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
