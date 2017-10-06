package ru.zaochno.zaochno.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.BaseExam;
import ru.zaochno.zaochno.data.model.Exam;
import ru.zaochno.zaochno.data.model.ExamFuture;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.ui.adapter.TrainingExamsAdapter;

public class TrainingExamsDialog extends DialogFragment {
    private Training training;
    private DialogRegisterListener dialogRegisterListener;
    private Context context;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_training_exams, container, false);
        ButterKnife.bind(this, rootView);

        tvTitle.setText(training.getName());
        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        Retrofit2Client.getInstance().getApi().getTrainingExams(training).enqueue(new Callback<DataResponseWrapper<List<ExamFuture>>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<List<ExamFuture>>> call, Response<DataResponseWrapper<List<ExamFuture>>> response) {
                if (response == null || response.body() == null) {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                TrainingExamsAdapter adapter = new TrainingExamsAdapter(response.body().getResponseObj(), new TrainingExamsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(BaseExam exam) {
                        if (dialogRegisterListener != null) {
                            dialogRegisterListener.onRegister(exam);
                            getDialog().cancel();
                        }
                    }
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<List<ExamFuture>>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @OnClick(R.id.iv_close)
    public void closeDialog() {
        getDialog().cancel();
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setDialogRegisterListener(DialogRegisterListener dialogRegisterListener) {
        this.dialogRegisterListener = dialogRegisterListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface DialogRegisterListener {
        void onRegister(BaseExam exam);
    }
}
