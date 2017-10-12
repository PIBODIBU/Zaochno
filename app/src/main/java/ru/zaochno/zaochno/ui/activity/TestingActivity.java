package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Answer;
import ru.zaochno.zaochno.data.model.Question;
import ru.zaochno.zaochno.data.model.Test;
import ru.zaochno.zaochno.data.model.UserAnswerSet;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.data.utils.DateUtils;
import ru.zaochno.zaochno.ui.adapter.AnswerListAdapter;
import ru.zaochno.zaochno.ui.dialog.ChooseAnswerDialog;

public class TestingActivity extends BaseNavDrawerActivity {
    public static final String TAG = "TestingActivity";
    public static final String INTENT_KEY_TEST_ID = "INTENT_KEY_TEST_ID";

    @BindView(R.id.tv_minute_counter)
    public TextView tvCounter;

    @BindView(R.id.tv_title_question)
    public TextView tvTitleQuestion;

    @BindView(R.id.tv_question_counter)
    public TextView tvQuestionCounter;

    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_question)
    public TextView tvQuestion;

    @BindView(R.id.recycler_view_answers)
    public RecyclerView recyclerViewAnswers;

    private AnswerListAdapter answerListAdapter;
    private ChooseAnswerDialog answerDialog = new ChooseAnswerDialog();

    private Integer testId;
    private Integer currentPosition = 0;
    private Test test;
    private UserAnswerSet userAnswerSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        ButterKnife.bind(this);

        if (!checkIntent())
            return;

        setupDrawer();
        setupRecyclerView();
        fetchTest();
    }

    private void setupRecyclerView() {
        answerListAdapter = new AnswerListAdapter(new LinkedList<Answer>(), this);

        recyclerViewAnswers.setAdapter(answerListAdapter);
        recyclerViewAnswers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnswers.setHasFixedSize(true);
    }

    private void setupAnswerSet() {
        userAnswerSet = new UserAnswerSet(AuthProvider.getInstance(this).getCurrentUser().getToken(), test.getId());
    }

    private void setupUi() {
        new CountDownTimer(test.getMinutes() * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCounter.setText("Осталось времени (мин): ".concat(DateUtils.millisToPattern(millisUntilFinished, DateUtils.PATTERN_MIN_SEC)));
            }

            public void onFinish() {
                Toast.makeText(TestingActivity.this, "Время вышло", Toast.LENGTH_LONG).show();
                finish();
            }

        }.start();
    }

    private void nextQuestion() {
        if (currentPosition == test.getQuestions().size()) {
            // No items left
            sendAnswers(userAnswerSet);
            return;
        }

        Answer answer = null;
        try {
            answer = getCurrentQuestion().getAnswers().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (answer == null) {
            incrementPosition();
            nextQuestion();
            return;
        }

        refreshUi(getCurrentQuestion());
    }

    private void sendAnswers(UserAnswerSet answerSet) {
        Retrofit2Client.getInstance().getApi().sendTestResult(answerSet).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                if (response == null || response.body() == null || response.body().getError() == null || response.body().getError()) {
                    Toast.makeText(TestingActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), R.string.test_result_sent, Toast.LENGTH_LONG).show();
                startActivity(new Intent(TestingActivity.this, TestListActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }

            @Override
            public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                Toast.makeText(TestingActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private Question getCurrentQuestion() {
        return test.getQuestions().get(currentPosition);
    }

    private void refreshUi(Question question) {
        Picasso.with(this)
                .load(question.getCoverUrl())
                .into(ivCover);

        tvQuestion.setText(question.getText());

        answerListAdapter.setAnswers(question.getAnswers());
        answerListAdapter.notifyDataSetChanged();

        tvTitleQuestion.setText("Вопрос ".concat(String.valueOf(currentPosition + 1)));
        tvQuestionCounter.setText(String.valueOf(currentPosition + 1).concat("/").concat(String.valueOf(test.getQuestions().size())));
    }

    private void chooseAnswer(Integer answerPosition) {
        Answer answer = null;
        UserAnswerSet.UserAnswer userAnswer = new UserAnswerSet.UserAnswer();

        try {
            answer = getCurrentQuestion().getAnswers().get(answerPosition - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if (answer == null || answer.getId() == null)
            userAnswer.setAnswerId(-1);
        else
            userAnswer.setAnswerId(answer.getId());

        if (getCurrentQuestion() == null || getCurrentQuestion().getId() == null)
            userAnswer.setQuestionId(-1);
        else
            userAnswer.setQuestionId(getCurrentQuestion().getId());

        userAnswerSet.getUserAnswers().add(userAnswer);

        answerDialog = new ChooseAnswerDialog();
        answerDialog.setQuestion(getCurrentQuestion());
        answerDialog.setAnswer(answer);
        answerDialog.setOnNextButtonClickListener(new ChooseAnswerDialog.OnNextButtonClickListener() {
            @Override
            public void onClick() {
                incrementPosition();
                nextQuestion();
            }
        });
        answerDialog.show(getSupportFragmentManager(), "AnswerDialog");
    }

    private void incrementPosition() {
        currentPosition++;
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
                        setupAnswerSet();
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
