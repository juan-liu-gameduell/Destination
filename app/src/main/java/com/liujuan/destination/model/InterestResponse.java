package com.liujuan.destination.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class InterestResponse {
    @SerializedName("place_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("rating")
    private double rating;
    @SerializedName("vicinity")
    private String address;
    @SerializedName("photos")
    private List<PhotoResponse> photos;
    @SerializedName("geometry")
    private Location location;

    private InterestResponse() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "InterestResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", photos=" + photos +
                ", location=" + location +
                '}';
    }
}
