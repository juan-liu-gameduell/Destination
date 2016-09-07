package com.liujuan.destination.model;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PlacePhoto {
    private int height;
    private int width;
    private String url;

    public PlacePhoto(int height, int width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "width:" + width + ",height:" + height + ",url:" + url;
    }
}
