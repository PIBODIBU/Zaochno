package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.filter.TrainingFilter;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.adapter.TrainingListAdapter;

public class TrainingListActivity extends BaseNavDrawerActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    @BindView(R.id.container_tabs)
    public View containerTabs;

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

        setupUi();
        fetchTrainings();
    }

    private void fetchTrainings() {
        Retrofit2Client.getInstance().getApi().getTrainings(new TrainingFilter(
                1000,
                "",
                null,
                0,
                1000
        )).enqueue(new Callback<DataResponseWrapper<List<Training>>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<List<Training>>> call, Response<DataResponseWrapper<List<Training>>> response) {
                if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                    Toast.makeText(TrainingListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                    return;
                }

                trainings.addAll(response.body().getResponseObj());

                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<List<Training>>> call, Throwable t) {
                Toast.makeText(TrainingListActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupUi() {
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivToolbarLogo);

        if (!AuthProvider.getInstance(this).isAuthenticated())
            containerTabs.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        adapter = new TrainingListAdapter(trainings, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
