package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    @SerializedName("Id")
    private int id;

    @SerializedName("CustomerId")
    private String customerId;

    @SerializedName("Date")
    private String date;

    @SerializedName("PetrolAmount")
    private float petrolAmount;

    @SerializedName("FreeAmount")
    private float freeAmount;

    @SerializedName("TotalPrice")
    private float totalPrice;

    @SerializedName("ConvertExceedingAmountToFree")
    private boolean convertExceedingAmountToFree;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPetrolAmount() {
        return petrolAmount;
    }

    public void setPetrolAmount(float petrolAmount) {
        this.petrolAmount = petrolAmount;
    }

    public float getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(float freeAmount) {
        this.freeAmount = freeAmount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isConvertExceedingAmountToFree() {
        return convertExceedingAmountToFree;
    }

    public void setConvertExceedingAmountToFree(boolean convertExceedingAmountToFree) {
        this.convertExceedingAmountToFree = convertExceedingAmountToFree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
