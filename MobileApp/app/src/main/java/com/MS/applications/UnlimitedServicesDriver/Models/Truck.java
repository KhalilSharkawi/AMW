package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Truck implements Serializable {

    @SerializedName("location")
    private  String location;

    @SerializedName("lat")
    private  Double lat;

    @SerializedName("lng")
    private  Double lng;

    @SerializedName("distances")
    private  Double distances;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getDistances() {
        return distances;
    }

    public void setDistances(Double distances) {
        this.distances = distances;
    }
}

