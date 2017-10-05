package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import ru.zaochno.zaochno.data.model.request.BaseTokenRequest;
import ru.zaochno.zaochno.data.utils.DateUtils;

public class Message implements BaseTokenRequest {
    @SerializedName("msgId")
    private Integer id;

    @SerializedName("isRead")
    private Boolean isRead;

    @SerializedName("date")
    private Long date;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("answer")
    private String answer;

    @SerializedName("token")
    private String token;

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Message(Integer id, Boolean isRead, Long date, String title, String message, String answer, String token) {
        this.id = id;
        this.isRead = isRead;
        this.date = date;
        this.title = title;
        this.message = message;
        this.answer = answer;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Long getDate() {
        return date - (DateUtils.HOUR * 3); // - 3 hours
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
