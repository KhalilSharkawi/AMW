package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckUserInfo implements Serializable {

    @SerializedName("user")
    private UserInfo user;

    @SerializedName("android-version")
    private String androidVersion;

    @SerializedName("ios-version")
    private String iosVersion;

    @SerializedName("google-url")
    private String GoogleURL;

    @SerializedName("appstore-url")
    private String AppStoreURL;

    public CheckUserInfo() {
    }

    public CheckUserInfo(UserInfo user, String androidVersion, String iosVersion, String googleURL, String appStoreURL) {
        this.user = user;
        this.androidVersion = androidVersion;
        this.iosVersion = iosVersion;
        GoogleURL = googleURL;
        AppStoreURL = appStoreURL;
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

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getGoogleURL() {
        return GoogleURL;
    }

    public void setGoogleURL(String googleURL) {
        GoogleURL = googleURL;
    }

    public String getAppStoreURL() {
        return AppStoreURL;
    }

    public void setAppStoreURL(String appStoreURL) {
        AppStoreURL = appStoreURL;
    }
}
