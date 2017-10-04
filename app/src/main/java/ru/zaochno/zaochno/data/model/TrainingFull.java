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

    @SerializedName("purchDate")
    private Long purchaseDate;

    @SerializedName("purchDuration")
    private Long durationHours;

    @SerializedName("progress")
    private Integer progress;

    @SerializedName("chapters")
    private List<Chapter> chapters;

    @SerializedName("tests")
    private List<Test> tests;

    public TrainingFull(Boolean full, String name, String htmlText, Long purchaseDate, Long durationHours, Integer progress, List<Chapter> chapters, List<Test> tests) {
        this.full = full;
        this.name = name;
        this.htmlText = htmlText;
        this.purchaseDate = purchaseDate;
        this.durationHours = durationHours;
        this.progress = progress;
        this.chapters = chapters;
        this.tests = tests;
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

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getDurationHours() {
        return durationHours;
    }

    public Long getDurationMillis() {
        if (durationHours == null)
            return 0L;

        return durationHours * 60 * 60 * 1000;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setDurationHours(Long durationHours) {
        this.durationHours = durationHours;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
