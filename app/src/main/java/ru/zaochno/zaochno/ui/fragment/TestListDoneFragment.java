package ru.zaochno.zaochno.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Test;
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

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        List<Test> tests = new ArrayList<>();
        tests.add(null);
        tests.add(null);
        tests.add(null);
        tests.add(null);
        tests.add(null);
        tests.add(null);

        adapter = new TestListDoneAdapter(tests, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
