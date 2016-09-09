package com.liujuan.destination.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotosAndIntroOfCityResponse {
    private String introductionUrl;
    private List<PhotoResponse> photos;

    public PhotosAndIntroOfCityResponse() {
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
