package com.liujuan.destination.model;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PointOfInterest {
    String name;
    String address;
    float rating;
    PlacePhoto[] photos;
    String id;

    public PointOfInterest(String name, String address, float rating, PlacePhoto[] photos, String id) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.photos = photos;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public PlacePhoto[] getPhotos() {
        return photos;
    }

    public void setPhotos(PlacePhoto[] photos) {
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "name:" + name + ",id:" + id + ",address:" + address + ",photo url:" + photos[0].toString();
    }
}
