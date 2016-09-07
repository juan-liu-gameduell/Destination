package com.liujuan.destination.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PlaceResponse {
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("status")
    private String status;
    @SerializedName("results")
    private List<InterestResponse> intests;

    private PlaceResponse(){

    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getStatus() {
        return status;
    }

    public List<InterestResponse> getIntests() {
        return intests;
    }
}
