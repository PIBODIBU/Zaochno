package ru.zaochno.zaochno.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

public class User extends BaseObservable {
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

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("organizationForm")
    private String organizationForm;

    @SerializedName("genDirectoryName")
    private String genDirectoryName;

    @SerializedName("inn")
    private String inn;

    @SerializedName("kpp")
    private String kpp;

    @SerializedName("checkingAccount")
    private String checkingAccount;

    @SerializedName("corAccount")
    private String corAccount;

    @SerializedName("bik")
    private String bik;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("region1")
    private String region1;

    @SerializedName("city1")
    private String city1;

    @SerializedName("address1")
    private String address1;

    @SerializedName("region2")
    private String region2;

    @SerializedName("city2")
    private String city2;

    @SerializedName("address2")
    private String address2;

    public User() {
    }

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

    public User(String name, String userNick, String password, String type, String region, String phone, String email, String photoUrl, String token, String companyName, String organizationForm, String genDirectoryName, String inn, String kpp, String checkingAccount, String corAccount, String bik, String bankName, String region1, String city1, String address1, String region2, String city2, String address2) {
        this.name = name;
        this.userNick = userNick;
        this.password = password;
        this.type = type;
        this.region = region;
        this.phone = phone;
        this.email = email;
        this.photoUrl = photoUrl;
        this.token = token;
        this.companyName = companyName;
        this.organizationForm = organizationForm;
        this.genDirectoryName = genDirectoryName;
        this.inn = inn;
        this.kpp = kpp;
        this.checkingAccount = checkingAccount;
        this.corAccount = corAccount;
        this.bik = bik;
        this.bankName = bankName;
        this.region1 = region1;
        this.city1 = city1;
        this.address1 = address1;
        this.region2 = region2;
        this.city2 = city2;
        this.address2 = address2;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
        notifyPropertyChanged(BR.userNick);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
        notifyPropertyChanged(BR.region);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhotoUrl() {
        if (photoUrl != null)
            if (!photoUrl.startsWith("http://") && !photoUrl.startsWith("https://"))
                photoUrl = "http://".concat(photoUrl);

        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        notifyPropertyChanged(BR.photoUrl);
    }

    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        notifyPropertyChanged(BR.token);
    }

    @Bindable
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        notifyPropertyChanged(BR.companyName);
    }

    @Bindable
    public String getOrganizationForm() {
        return organizationForm;
    }

    public void setOrganizationForm(String organizationForm) {
        this.organizationForm = organizationForm;
        notifyPropertyChanged(BR.organizationForm);
    }

    @Bindable
    public String getGenDirectoryName() {
        return genDirectoryName;
    }

    public void setGenDirectoryName(String genDirectoryName) {
        this.genDirectoryName = genDirectoryName;
        notifyPropertyChanged(BR.genDirectoryName);
    }

    @Bindable
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
        notifyPropertyChanged(BR.inn);
    }

    @Bindable
    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
        notifyPropertyChanged(BR.kpp);
    }

    @Bindable
    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
        notifyPropertyChanged(BR.checkingAccount);
    }

    @Bindable
    public String getCorAccount() {
        return corAccount;
    }

    public void setCorAccount(String corAccount) {
        this.corAccount = corAccount;
        notifyPropertyChanged(BR.corAccount);
    }

    @Bindable
    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
        notifyPropertyChanged(BR.bik);
    }

    @Bindable
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
        notifyPropertyChanged(BR.bankName);
    }

    @Bindable
    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
        notifyPropertyChanged(BR.region1);
    }

    @Bindable
    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
        notifyPropertyChanged(BR.city1);
    }

    @Bindable
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
        notifyPropertyChanged(BR.address1);
    }

    @Bindable
    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
        notifyPropertyChanged(BR.region2);
    }

    @Bindable
    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
        notifyPropertyChanged(BR.city2);
    }

    @Bindable
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
        notifyPropertyChanged(BR.address2);
    }
}
