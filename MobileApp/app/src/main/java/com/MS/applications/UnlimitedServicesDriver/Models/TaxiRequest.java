package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaxiRequest implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("cost")
    private double cost;

    @SerializedName("comment")
    private String comment;

    @SerializedName("from_lat")
    private double fromLat;

    @SerializedName("from_lng")
    private double fromLng;

    @SerializedName("location")
    private String location;

    @SerializedName("to_lat")
    private double toLat;

    @SerializedName("to_lng")
    private double toLng;

    @SerializedName("accepted_at")
    private String acceptedAt;

    @SerializedName("rejected_at")
    private String rejectedAt;

    @SerializedName("on_going_at")
    private String onGoingAt;

    @SerializedName("arrived_at")
    private String arrivedAt;

    @SerializedName("completed_at")
    private String completedAt;

    @SerializedName("status")
    private int status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("driverName")
    private String driverName;

    @SerializedName("taxiModel")
    private String taxiModel;

    @SerializedName("taxiNumber")
    private String taxiNumber;

    @SerializedName("taxiImage")
    private String taxiImage;

    @SerializedName("customer")
    private UserInfo customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getFromLat() {
        return fromLat;
    }

    public void setFromLat(double fromLat) {
        this.fromLat = fromLat;
    }

    public double getFromLng() {
        return fromLng;
    }

    public void setFromLng(double fromLng) {
        this.fromLng = fromLng;
    }

    public double getToLat() {
        return toLat;
    }

    public void setToLat(double toLat) {
        this.toLat = toLat;
    }

    public double getToLng() {
        return toLng;
    }

    public void setToLng(double toLng) {
        this.toLng = toLng;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(String rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public String getOnGoingAt() {
        return onGoingAt;
    }

    public void setOnGoingAt(String onGoingAt) {
        this.onGoingAt = onGoingAt;
    }

    public String getArrivedAt() {
        return arrivedAt;
    }

    public void setArrivedAt(String arrivedAt) {
        this.arrivedAt = arrivedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTaxiModel() {
        return taxiModel;
    }

    public void setTaxiModel(String taxiModel) {
        this.taxiModel = taxiModel;
    }

    public String getTaxiNumber() {
        return taxiNumber;
    }

    public void setTaxiNumber(String taxiNumber) {
        this.taxiNumber = taxiNumber;
    }

    public String getTaxiImage() {
        return taxiImage;
    }

    public void setTaxiImage(String taxiImage) {
        this.taxiImage = taxiImage;
    }

    public UserInfo getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfo customer) {
        this.customer = customer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
