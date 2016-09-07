package com.liujuan.destination.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotosOfCityResponse {
    private List<PhotoResponse> photos;

    public PhotosOfCityResponse() {
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }
}
