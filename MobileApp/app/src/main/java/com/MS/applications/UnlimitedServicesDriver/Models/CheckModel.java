package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

public class CheckModel {

    @SerializedName("user")
    private UserInfo user;

    @SerializedName("android-version")
    private String androidVersion;

    @SerializedName("google-url")
    private String googleUrl;

    public CheckModel() {
    }

    public CheckModel(UserInfo user, String androidVersion, String googleUrl) {
        this.user = user;
        this.androidVersion = androidVersion;
        this.googleUrl = googleUrl;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }
}
