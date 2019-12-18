package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class APIResponse<T> implements Serializable {

    @SerializedName("result")
    private String result;

    @SerializedName("content")
    private T content;

    @SerializedName("error_des")
    private String errorDes;

    @SerializedName("error_code")
    private int errorCode;

    public APIResponse() {
    }

    public APIResponse(String result, T content, String errorDes, int errorCode) {
        this.result = result;
        this.content = content;
        this.errorDes = errorDes;
        this.errorCode = errorCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getErrorDes() {
        return errorDes;
    }

    public void setErrorDes(String errorDes) {
        this.errorDes = errorDes;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
