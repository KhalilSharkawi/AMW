package com.MS.applications.UnlimitedServicesDriver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PagedAPIResponse<T> implements Serializable {

    @SerializedName("result")
    private String result;

    @SerializedName("content")
    private PagedContent<T> content;

    @SerializedName("error_des")
    private String errorDes;

    @SerializedName("error_code")
    private int errorCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PagedContent<T> getContent() {
        return content;
    }

    public void setContent(PagedContent<T> content) {
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
