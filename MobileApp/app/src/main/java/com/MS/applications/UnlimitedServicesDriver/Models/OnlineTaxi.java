package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

public class OnlineTaxi {
    @SerializedName("id")
    private int id;

    @SerializedName("driver_office_id")
    private int driverOfficeId;

    @SerializedName("driver_name")
    private String driverName;

    @SerializedName("number")
    private String number;

    @SerializedName("customer_model")
    private String model;

    @SerializedName("image")
    private String image;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("licence_date")
    private String licenceDate;

    @SerializedName("price_hour")
    private double pricePerHour;

    @SerializedName("status")
    private int status;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("price_km")
    private double pricePerKm;

    @SerializedName("distances")
    private double distances;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverOfficeId() {
        return driverOfficeId;
    }

    public void setDriverOfficeId(int driverOfficeId) {
        this.driverOfficeId = driverOfficeId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(String licenceDate) {
        this.licenceDate = licenceDate;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getDistances() {
        return distances;
    }

    public void setDistances(double distances) {
        this.distances = distances;
    }
}
