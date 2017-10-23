package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Exam;
import ru.zaochno.zaochno.data.model.Region;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.model.response.ExamRegisterResponse;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.dialog.TrainingExamsDialog;

public class ExamNewActivity extends BaseNavDrawerActivity {
    private static final String TAG = "ExamNewActivity";
    public static final String INTENT_KEY_TRAINING_MODEL = "INTENT_KEY_TRAINING_MODEL";

   /* @BindView(R.id.calendar_view)
    public CustomCalendarView calendarView;*/

    @BindView(R.id.tv_training_name)
    public TextView tvTrainingName;

    @BindView(R.id.spinner_region)
    public Spinner spinner;

    @BindView(R.id.spinner_exams)
    public Spinner spinnerExams;

    private Training training;
    private Exam exam = new Exam();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_new);
        ButterKnife.bind(this);

        if (!checkIntent())
            return;
        setupDrawer();
        setupUi();
    }

    private Boolean checkIntent() {
        if (getIntent() == null || getIntent().getExtras() == null)
            return false;

        if (!getIntent().getExtras().containsKey(INTENT_KEY_TRAINING_MODEL))
            return false;

        training = ((Training) getIntent().getExtras().get(INTENT_KEY_TRAINING_MODEL));

        return training != null;
    }

    private void setupUi() {
        tvTrainingName.setText(training.getName());

        /*((ImageView) calendarView.findViewById(R.id.leftButton)).setImageResource(R.drawable.ic_keyboard_arrow_left_grey_24dp);
        ((ImageView) calendarView.findViewById(R.id.rightButton)).setImageResource(R.drawable.ic_keyboard_arrow_right_grey_24dp);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                exam.setDate(DateUtils.millisToPattern(date.getTime(), DateUtils.PATTERN_DATE));
            }

            @Override
            public void onMonthChanged(Date date) {

            }
        });*/

        setupSpinner();
    }

    private void setupSpinner() {
        Retrofit2Client.getInstance().getApi().getRegions().enqueue(new Callback<DataResponseWrapper<List<Region>>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<List<Region>>> call, Response<DataResponseWrapper<List<Region>>> response) {
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(ExamNewActivity.this, R.layout.spinner_dropdown_item_tall);
                adapter.add(new Region("Выберите регион из списка"));
                adapter.addAll(response.body().getResponseObj());
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        exam.setRegion(adapter.getItem(i).getName());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<List<Region>>> call, Throwable t) {
                Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });

        Retrofit2Client.getInstance().getApi().getTrainingExams(training).enqueue(new Callback<DataResponseWrapper<List<Exam>>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<List<Exam>>> call, Response<DataResponseWrapper<List<Exam>>> response) {
                final ArrayAdapter<Exam> adapter = new ArrayAdapter<>(ExamNewActivity.this, android.R.layout.simple_list_item_1);
                adapter.addAll(response.body().getResponseObj());
                spinnerExams.setAdapter(adapter);
                spinnerExams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        exam.setId(adapter.getItem(i).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<List<Exam>>> call, Throwable t) {
                Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void register() {
        exam.setToken(AuthProvider.getInstance(this).getCurrentUser().getToken());

        Retrofit2Client.getInstance().getApi().registerOnExam(exam).enqueue(new Callback<ExamRegisterResponse>() {
            @Override
            public void onResponse(Call<ExamRegisterResponse> call, Response<ExamRegisterResponse> response) {
                if (response == null || response.body() == null || !response.body().getRegistered()) {
                    Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), R.string.exam_registered, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<ExamRegisterResponse> call, Throwable t) {
                Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    //@OnClick(R.id.btn_schedule)
    public void showScheduleDialog() {
      /*  TrainingExamsDialog dialog = new TrainingExamsDialog();
        dialog.setTraining(training);
        dialog.setContext(this);
        dialog.setDialogRegisterListener(new TrainingExamsDialog.DialogRegisterListener() {
            @Override
            public void onRegister(Exam exam) {
                exam.setToken(AuthProvider.getInstance(ExamNewActivity.this).getCurrentUser().getToken());

                Retrofit2Client.getInstance().getApi().registerOnExam(exam).enqueue(new Callback<BaseErrorResponse>() {
                    @Override
                    public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                        if (response == null || response.body() == null || response.body().getError() == null || response.body().getError()) {
                            Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), R.string.exam_registered, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                        Toast.makeText(ExamNewActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show(getSupportFragmentManager(), "TrainingExamsDialog");*/
    }
}