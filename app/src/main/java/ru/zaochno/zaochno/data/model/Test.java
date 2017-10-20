package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.utils.UrlUtils;

public class Test extends TokenRequest implements Serializable {
    @SerializedName("testId")
    private Integer id;

    @SerializedName("testName")
    private String name;

    @SerializedName("testDesc")
    private String description;

    @SerializedName("cover")
    private String coverUrl;

    @SerializedName("trenningValidity")
    private Long trainingValidity;

    @SerializedName("progress")
    private Integer progress;

    @SerializedName("minutes")
    private Integer minutes;

    @SerializedName("questions")
    private List<Question> questions;

    public Test(String token) {
        super(token);
    }

    public Test(String token, Integer id) {
        this.token = token;
        this.id = id;
    }

    public Test(Integer id, String name, String description, String coverUrl, Long trainingValidity, Integer progress, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverUrl = coverUrl;
        this.trainingValidity = trainingValidity;
        this.progress = progress;
        this.questions = questions;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return UrlUtils.htmlPathToUrl(coverUrl);
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getTrainingValidity() {
        return trainingValidity;
    }

    public void setTrainingValidity(Long trainingValidity) {
        this.trainingValidity = trainingValidity;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
