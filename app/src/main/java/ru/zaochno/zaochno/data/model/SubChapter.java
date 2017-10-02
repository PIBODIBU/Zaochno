package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubChapter implements Serializable {
    @SerializedName("subChapterId")
    private Integer id;

    @SerializedName("subChapterName")
    private String name;

    @SerializedName("htmlText")
    private String htmlText;

    public SubChapter(Integer id, String name, String htmlText) {
        this.id = id;
        this.name = name;
        this.htmlText = htmlText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }
}
