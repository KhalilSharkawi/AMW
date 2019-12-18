package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

public class ResetPass {

    @SerializedName("date")
    private String date;

    @SerializedName("timezone_type")
    private int timezoneType;

    @SerializedName("timezone")
    private String timezone;
}
