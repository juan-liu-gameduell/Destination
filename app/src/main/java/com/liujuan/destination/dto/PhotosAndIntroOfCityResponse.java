package com.liujuan.destination.dto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotosAndIntroOfCityResponse extends GoogleApiResponse {
    private String introductionUrl;
    private List<PhotoResponse> photos;

    private PhotosAndIntroOfCityResponse(String status, String errorMessage) {
        super(status, errorMessage);
    }

    public static PhotosAndIntroOfCityResponse create() {
        return new PhotosAndIntroOfCityResponse(STATUS_OK, "");
    }

    public static PhotosAndIntroOfCityResponse createError(String status, String errorMessage) {
        return new PhotosAndIntroOfCityResponse(status, errorMessage);
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }

    public String getIntroductionUrl() {
        return introductionUrl;
    }

    public void setIntroductionUrl(String introductionUrl) {
        this.introductionUrl = introductionUrl;
    }
}
