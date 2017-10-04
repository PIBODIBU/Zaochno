package ru.zaochno.zaochno.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Chapter extends BaseChapter implements Serializable {
    transient private Integer position;

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

    @NonNull
    public Integer getPosition() {
        if (position == null)
            return -1;
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
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
