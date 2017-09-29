package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import ru.zaochno.zaochno.data.model.request.BaseTokenRequest;

public class Message implements BaseTokenRequest {
    @SerializedName("msgId")
    private Integer id;

    @SerializedName("isRead")
    private Boolean isRead;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Message(Integer id, Boolean isRead, String title, String message) {
        this.id = id;
        this.isRead = isRead;
        this.title = title;
        this.message = message;
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

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
