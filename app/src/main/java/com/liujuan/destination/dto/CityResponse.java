package com.liujuan.destination.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CityResponse extends GoogleApiResponse {
    @SerializedName("result")
    private CityResultResponse resultResponse;

    public CityResponse(String status, String errorMessage) {
        super(status, errorMessage);
    }

    public void setResultResponse(CityResultResponse resultResponse) {
        this.resultResponse = resultResponse;
    }

    public CityResultResponse getResultResponse() {
        return resultResponse;
    }
}
