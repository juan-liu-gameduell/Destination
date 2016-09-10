package com.liujuan.destination.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PlaceResponse extends GoogleApiResponse {
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("results")
    private List<InterestResponse> interests;

    private PlaceResponse(String status, String errorMessage) {
        super(status, errorMessage);
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<InterestResponse> getInterests() {
        return interests;
    }
}
