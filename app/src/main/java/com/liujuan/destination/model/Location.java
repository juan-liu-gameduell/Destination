package com.liujuan.destination.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Location {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
