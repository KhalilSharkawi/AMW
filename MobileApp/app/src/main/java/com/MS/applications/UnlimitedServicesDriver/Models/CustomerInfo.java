package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerInfo implements Serializable {

    @SerializedName("_5daysAllownce")
    private float _5daysAllownce ;
    @SerializedName("_monthlyAllownce")
    private float _monthlyAllownce;
    @SerializedName("_free")
    private float _free;

    public float get_5daysAllownce() {
        return _5daysAllownce;
    }

    public void set_5daysAllownce(float _5daysAllownce) {
        this._5daysAllownce = _5daysAllownce;
    }

    public float get_monthlyAllownce() {
        return _monthlyAllownce;
    }

    public void set_monthlyAllownce(float _monthlyAllownce) {
        this._monthlyAllownce = _monthlyAllownce;
    }

    public float get_free() {
        return _free;
    }

    public void set_free(float _free) {
        this._free = _free;
    }
}
