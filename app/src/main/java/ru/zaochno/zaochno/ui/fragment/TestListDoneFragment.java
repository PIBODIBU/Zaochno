package ru.zaochno.zaochno.ui.fragment;

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
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.adapter.TestListDoneAdapter;

public class TestListDoneFragment extends Fragment {
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private TestListDoneAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);
        ButterKnife.bind(this, view);

        fetchData();

        return view;
    }

    private void fetchData() {
        Retrofit2Client.getInstance().getApi().getDoneTests(new Token(AuthProvider.getInstance(getActivity()).getCurrentUser().getToken()))
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
        adapter = new TestListDoneAdapter(tests, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
