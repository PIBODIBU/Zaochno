package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import ru.zaochno.zaochno.utils.UrlUtils;

public class Question implements Serializable {
    @SerializedName("questionId")
    private Integer id;

    @SerializedName("questionText")
    private String text;

    @SerializedName("questionCover")
    private String coverUrl;

    @SerializedName("hintRight")
    private String hintRight;

    @SerializedName("hintnWrong")
    private String hintWrong;

    @SerializedName("answers")
    private List<Answer> answers;

    public Question() {
    }

    public void randomizeAnswers() {
        if (answers != null)
            Collections.shuffle(answers);
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

    public String getHintRight() {
        return hintRight;
    }

    public void setHintRight(String hintRight) {
        this.hintRight = hintRight;
    }

    public String getHintWrong() {
        return hintWrong;
    }

    public void setHintWrong(String hintWrong) {
        this.hintWrong = hintWrong;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
