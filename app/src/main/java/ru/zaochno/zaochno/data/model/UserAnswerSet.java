package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

import ru.zaochno.zaochno.data.model.request.TokenRequest;

public class UserAnswerSet extends TokenRequest {
    @SerializedName("testId")
    private Integer testId;

    @SerializedName("results")
    private List<UserAnswer> userAnswers;

    public UserAnswerSet(String token, Integer testId) {
        super(token);
        this.testId = testId;
        this.userAnswers = new LinkedList<>();
    }

    public UserAnswerSet(String token, Integer testId, List<UserAnswer> userAnswers) {
        super(token);
        this.testId = testId;
        this.userAnswers = userAnswers;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public static class UserAnswer {
        @SerializedName("questionId")
        private Integer questionId;

        @SerializedName("answerId")
        private Integer answerId;

        public UserAnswer() {
        }

        public UserAnswer(Integer questionId, Integer answerId) {
            this.questionId = questionId;
            this.answerId = answerId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Integer getAnswerId() {
            return answerId;
        }

        public void setAnswerId(Integer answerId) {
            this.answerId = answerId;
        }
    }
}
