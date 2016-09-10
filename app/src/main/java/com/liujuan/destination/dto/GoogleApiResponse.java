package com.liujuan.destination.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/9.
 */
public abstract class GoogleApiResponse {
    public static final String STATUS_OK = "OK";
    @SerializedName("error_message")
    private String errorMessage;
    @SerializedName("status")
    private String status;

    public GoogleApiResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public boolean hasError() {
        return !status.equals(STATUS_OK);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
