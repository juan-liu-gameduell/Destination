package com.liujuan.destination.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/4.
 */
public class City {
    private String name;
    private ArrayList<String> images;
    private long longitude;
    private long latitude;

    public City(String name, String image) {
        this.name = name;
        this.images = new ArrayList<>();
        this.images.add(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
