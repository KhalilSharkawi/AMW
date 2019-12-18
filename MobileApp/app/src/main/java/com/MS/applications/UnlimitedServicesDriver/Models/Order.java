package com.MS.applications.UnlimitedServicesDriver.Models;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable  {

    @SerializedName("id")
    private int id;

    @SerializedName("image")
    private String image;

    @SerializedName("location")
    private String location;

    @SerializedName("location_from")
    private String locationFrom;

    @SerializedName("lat_from")
    private Double latFrom;

    @SerializedName("lng_from")
    private Double lngFrom;

    @SerializedName("location_to")
    private String locationTo;

    @SerializedName("lat_to")
    private Double latTo;

    @SerializedName("lng_to")
    private Double lngTo;

    @SerializedName("location_current")
    private String locationCurrent;

    @SerializedName("lat_current")
    private Double latCurrent;

    @SerializedName("lng_current")
    private Double lngCurrent;

    @SerializedName("lat")
    private  Double lat;

    @SerializedName("lng")
    private  Double lng;

    @SerializedName("rating")
    private  int rating;

    @SerializedName("status")
    private  int status;

    @SerializedName("comment")
    private  String comment;

    @SerializedName("customer_id")
    private  int customerId;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("truck_id")
    private  int truckId;

    //@SerializedName("customer")
    private UserInfo customer;

    @SerializedName("created_at")
    private String createdAt;

    //@SerializedName("truck")
    private Truck truck;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        try {
            return lat;
        }
        catch (Exception e) {
            return 0.0;
        }
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public UserInfo getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfo customer) {
        this.customer = customer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public Double getLatFrom() {
        try {
            return latFrom;
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public void setLatFrom(Double latFrom) {
        this.latFrom = latFrom;
    }

    public Double getLngFrom() {
        return lngFrom;
    }

    public void setLngFrom(Double lngFrom) {
        this.lngFrom = lngFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public Double getLatTo() {
        try {
            return latTo;
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public void setLatTo(Double latTo) {
        this.latTo = latTo;
    }

    public Double getLngTo() {
        return lngTo;
    }

    public void setLngTo(Double lngTo) {
        this.lngTo = lngTo;
    }

    public String getLocationCurrent() {
        return locationCurrent;
    }

    public void setLocationCurrent(String locationCurrent) {
        this.locationCurrent = locationCurrent;
    }

    public Double getLatCurrent() {
        try {
            return latCurrent;
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public void setLatCurrent(Double latCurrent) {
        this.latCurrent = latCurrent;
    }

    public Double getLngCurrent() {
        return lngCurrent;
    }

    public void setLngCurrent(Double lngCurrent) {
        this.lngCurrent = lngCurrent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
