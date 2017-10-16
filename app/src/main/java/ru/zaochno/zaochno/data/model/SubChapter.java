package ru.zaochno.zaochno.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubChapter extends BaseChapter implements Serializable {
    transient private Integer position;
    transient private Integer parentId;

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

    @NonNull
    public Integer getPosition() {
        if (position == null)
            return -1;
        return position;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    @Override
    public String toString() {
        return name;
    }
}
