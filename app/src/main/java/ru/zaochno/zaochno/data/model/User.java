package ru.zaochno.zaochno.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    private String name;

    private String userNick;
    private String password;

    @SerializedName("type")
    private String type;

    @SerializedName("region")
    private String region;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("imgAvatar")
    private String photoUrl;

    @SerializedName("token")
    private String token;

    public User(String userNick, String password) {
        this.userNick = userNick;
        this.password = password;
    }

    public User(String name, String email, String photoUrl) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public User(String name, String type, String region, String phone, String email, String photoUrl, String token) {
        this.name = name;
        this.type = type;
        this.region = region;
        this.phone = phone;
        this.email = email;
        this.photoUrl = photoUrl;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        if (photoUrl != null)
            if (!photoUrl.startsWith("http://") && !photoUrl.startsWith("https://"))
                photoUrl = "http://".concat(photoUrl);

        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
