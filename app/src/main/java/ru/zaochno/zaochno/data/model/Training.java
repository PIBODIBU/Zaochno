package ru.zaochno.zaochno.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Training extends BaseObservable implements Serializable {
    @SerializedName("trenningId")
    private Integer id;

    @SerializedName("isPayment")
    private Boolean isPayed;

    @SerializedName("treningName")
    private String name;

    @SerializedName("trenningIcoUrl")
    private String imgUrl;

    @SerializedName("treningShortText")
    private String shortText;

    @SerializedName("treningFullText")
    private String fullText;

    @SerializedName("treningFavorite")
    private Boolean isFavourite;

    @SerializedName("progress")
    private Double progress;

    @SerializedName("validity")
    private Long validity;

    @SerializedName("categories")
    private List<Category> categories;

    @SerializedName("treningPrice")
    private List<TrainingPrice> trainingPrices;

    @SerializedName("token")
    private String userToken;

    public Training(Integer id) {
        this.id = id;
    }

    public Training(Integer id, Boolean isPayed, String name, String imgUrl, String shortText, String fullText, Boolean isFavourite, List<Category> categories, List<TrainingPrice> trainingPrices) {
        this.id = id;
        this.isPayed = isPayed;
        this.name = name;
        this.imgUrl = imgUrl;
        this.shortText = shortText;
        this.fullText = fullText;
        this.isFavourite = isFavourite;
        this.categories = categories;
        this.trainingPrices = trainingPrices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public Boolean getPayed() {
        return isPayed;
    }

    public void setPayed(Boolean payed) {
        isPayed = payed;
        notifyPropertyChanged(BR.payed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        if (imgUrl != null && imgUrl != "")
            if (!imgUrl.startsWith("http://") && !imgUrl.startsWith("https://"))
                imgUrl = "http://".concat(imgUrl);

        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public List<TrainingPrice> getTrainingPrices() {
        return trainingPrices;
    }

    public void setTrainingPrices(List<TrainingPrice> trainingPrices) {
        this.trainingPrices = trainingPrices;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public TrainingPrice getLowestPrice() {
        if (getTrainingPrices() == null)
            return null;

        Collections.sort(getTrainingPrices(), new Comparator<TrainingPrice>() {
            @Override
            public int compare(TrainingPrice trainingPrice, TrainingPrice t1) {
                return trainingPrice.getPrice().compareTo(t1.getPrice());
            }
        });

        return getTrainingPrices().get(0);
    }

    public TrainingPrice getHighestPrice() {
        if (getTrainingPrices() == null)
            return null;

        Collections.sort(getTrainingPrices(), new Comparator<TrainingPrice>() {
            @Override
            public int compare(TrainingPrice trainingPrice, TrainingPrice t1) {
                return t1.getPrice().compareTo(trainingPrice.getPrice());
            }
        });

        return getTrainingPrices().get(0);
    }
}
