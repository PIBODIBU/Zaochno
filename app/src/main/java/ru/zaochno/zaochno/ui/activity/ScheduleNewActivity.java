package ru.zaochno.zaochno.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class ScheduleNewActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    public CustomCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_new);
        ButterKnife.bind(this);

        setupUi();
    }

    private void setupUi() {
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
    }
}
