package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrainingFull implements Serializable {
    @SerializedName("full")
    private Boolean full;

    @SerializedName("name")
    private String name;

    @SerializedName("htmlText")
    private String htmlText;

    @SerializedName("chapters")
    private List<Chapter> chapters;

//    @SerializedName("tests")
//    private List<Test> tests;

    public TrainingFull(Boolean full, String name, String htmlText, List<Chapter> chapters, List<Test> tests) {
        this.full = full;
        this.name = name;
        this.htmlText = htmlText;
        this.chapters = chapters;
        //this.tests = tests;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    /*public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }*/
}
