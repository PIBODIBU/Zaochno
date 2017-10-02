package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {
    @SerializedName("answerId")
    private Integer id;

    @SerializedName("answerText")
    private String text;

    @SerializedName("htmlText")
    private String htmlText;

    @SerializedName("correct")
    private Boolean isCorrect;

    public Answer(Integer id, String text, String htmlText, Boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.htmlText = htmlText;
        this.isCorrect = isCorrect;
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

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
