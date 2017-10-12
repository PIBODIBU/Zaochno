package ru.zaochno.zaochno.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.data.model.Token;
import ru.zaochno.zaochno.data.model.TrainingId;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.activity.TestingActivity;
import ru.zaochno.zaochno.ui.adapter.TestListActiveAdapter;

public class TestListActiveFragment extends Fragment {
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private TestListActiveAdapter adapter;
    private Integer trainingId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);
        ButterKnife.bind(this, view);

        fetchData();

        return view;
    }

    private void fetchData() {
        TrainingId trainingIdModel = new TrainingId(AuthProvider.getInstance(getActivity()).getCurrentUser().getToken());

        if (getTrainingId() != -1)
            trainingIdModel.setId(getTrainingId());

        Retrofit2Client.getInstance().getApi().getActiveTests(trainingIdModel)
                .enqueue(new Callback<DataResponseWrapper<List<Test>>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<List<Test>>> call, Response<DataResponseWrapper<List<Test>>> response) {
                        if (response == null || response.body() == null) {
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        setupRecyclerView(response.body().getResponseObj());
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<List<Test>>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setupRecyclerView(List<Test> tests) {
        adapter = new TestListActiveAdapter(tests, getActivity());
        adapter.setActionListener(new TestListActiveAdapter.ActionListener() {
            @Override
            public void onTestContinue(Test test) {
                startActivity(new Intent(getActivity(), TestingActivity.class)
                        .putExtra(TestingActivity.INTENT_KEY_TEST_ID, test.getId()));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
}
