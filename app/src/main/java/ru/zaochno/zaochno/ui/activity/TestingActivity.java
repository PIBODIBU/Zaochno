package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Question;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;

public class TestingActivity extends BaseNavDrawerActivity {
    public static final String TAG = "TestingActivity";
    public static final String INTENT_KEY_TEST_ID = "INTENT_KEY_TEST_ID";

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_question)
    public TextView tvQuestion;

    @BindView(R.id.tv_answers)
    public TextView tvAnswers;

    /*@BindView(R.id.btn_1)
    public Button btn1;
    @BindView(R.id.btn_2)
    public Button btn2;
    @BindView(R.id.btn_3)
    public Button btn3;
    @BindView(R.id.btn_4)
    public Button btn4;*/

    private Integer testId;
    private Integer currentPosition = 0;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        ButterKnife.bind(this);

        if (!checkIntent())
            return;
        setupDrawer();
        fetchTest();
    }

    private void setupUi() {
        tvTitle.setText(test.getName());
    }

    private void nextQuestion() {
        if (currentPosition == test.getQuestions().size()) {
            // No items left
            startActivity(new Intent(TestingActivity.this, TestListActivity.class));
            finish();
            return;
        }

        refreshUi(test.getQuestions().get(currentPosition));

        currentPosition++;
    }

    private void refreshUi(Question question) {
        Picasso.with(this)
                .load(question.getCoverUrl())
                .into(ivCover);

        tvQuestion.setText(question.getText());

        String answers = "";
        for (int i = 0; i < question.getAnswers().size(); i++)
            if (question.getAnswers().get(i) != null)
                answers = answers
                        .concat("Вариант ")
                        .concat(String.valueOf(i + 1))
                        .concat(". ")
                        .concat(question.getAnswers().get(i).getText())
                        .concat("\n");
        tvAnswers.setText(answers);
    }

    private void chooseAnswer(Integer answer) {
        nextQuestion();
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void buttonClick(Button button) {
        switch (button.getId()) {
            case R.id.btn_1:
                chooseAnswer(1);
                return;
            case R.id.btn_2:
                chooseAnswer(2);
                return;
            case R.id.btn_3:
                chooseAnswer(3);
                return;
            case R.id.btn_4:
                chooseAnswer(4);
                return;
        }
    }

    private void fetchTest() {
        Retrofit2Client.getInstance().getApi().getTest(new Test(AuthProvider.getInstance(this).getCurrentUser().getToken(), testId))
                .enqueue(new Callback<DataResponseWrapper<Test>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<Test>> call, Response<DataResponseWrapper<Test>> response) {
                        if (response == null || response.body() == null) {
                            Toast.makeText(TestingActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                            return;
                        }

                        setTest(response.body().getResponseObj());
                        setupUi();
                        nextQuestion();
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<Test>> call, Throwable t) {
                        Toast.makeText(TestingActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean checkIntent() {
        if (getIntent() == null || getIntent().getExtras() == null)
            return false;

        if (!getIntent().getExtras().containsKey(INTENT_KEY_TEST_ID))
            return false;

        testId = getIntent().getExtras().getInt(INTENT_KEY_TEST_ID, -1);

        return testId != -1;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
