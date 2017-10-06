package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ru.zaochno.zaochno.utils.UrlUtils;

public class Question implements Serializable {
    @SerializedName("questionId")
    private Integer id;

    @SerializedName("questionText")
    private String text;

    @SerializedName("questionCover")
    private String coverUrl;

    @SerializedName("answers")
    private List<Answer> answers;

    public Question(Integer id, String text, String coverUrl, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.coverUrl = coverUrl;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCoverUrl() {
        return UrlUtils.absolutePathToUrl(coverUrl);
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
