package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;

public class ScheduleNewActivity extends BaseNavDrawerActivity {
    public static final String INTENT_KEY_TRAINING_MODEL = "INTENT_KEY_TRAINING_MODEL";
    public static final String INTENT_KEY_TRAINING_FULL_MODEL = "INTENT_KEY_TRAINING_FULL_MODEL";

    @BindView(R.id.calendar_view)
    public CustomCalendarView calendarView;

    @BindView(R.id.tv_training_name)
    public TextView tvTrainingName;

    @BindView(R.id.spinner_region)
    public Spinner spinner;

    private Training training;
    private TrainingFull trainingFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_new);
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

        if (!getIntent().getExtras().containsKey(INTENT_KEY_TRAINING_FULL_MODEL))
            return false;

        training = ((Training) getIntent().getExtras().get(INTENT_KEY_TRAINING_MODEL));
        trainingFull = ((TrainingFull) getIntent().getExtras().get(INTENT_KEY_TRAINING_FULL_MODEL));

        if (training == null || trainingFull == null)
            return false;
        else
            return true;
    }

    private void setupUi() {
        tvTrainingName.setText(training.getName());

        ((ImageView) calendarView.findViewById(R.id.leftButton)).setImageResource(R.drawable.ic_keyboard_arrow_left_grey_24dp);
        ((ImageView) calendarView.findViewById(R.id.rightButton)).setImageResource(R.drawable.ic_keyboard_arrow_right_grey_24dp);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {

            }

            @Override
            public void onMonthChanged(Date date) {

            }
        });

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Выберите регион из списка");
        adapter.add("Регион №1");
        adapter.add("Регион №2");
        adapter.add("Регион №3");
        adapter.add("Регион №4");
        adapter.add("Регион №5");

        spinner.setAdapter(adapter);
    }
}
