package com.liujuan.destination.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CityResultResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("place_id")
    private String id;
    @SerializedName("geometry")
    private Location location;
    @SerializedName("photos")
    private List<PhotoResponse> photoResponseList;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public List<PhotoResponse> getPhotoResponseList() {
        return photoResponseList;
    }

    @Override
    public String toString() {
        return "CityResultResponse{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", location=" + location +
                ", photoResponseList=" + photoResponseList +
                '}';
    }
}
