package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable {
    @SerializedName("chapterId")
    private Integer id;

    @SerializedName("chapterName")
    private String name;

    @SerializedName("htmlText")
    private String htmlText;

    @SerializedName("subChapters")
    private List<SubChapter> subChapters;

    public Chapter(Integer id, String name, String htmlText, List<SubChapter> subChapters) {
        this.id = id;
        this.name = name;
        this.htmlText = htmlText;
        this.subChapters = subChapters;
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

    public List<SubChapter> getSubChapters() {
        return subChapters;
    }

    public void setSubChapters(List<SubChapter> subChapters) {
        this.subChapters = subChapters;
    }
}
