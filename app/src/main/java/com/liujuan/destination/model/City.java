package com.liujuan.destination.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/4.
 */
public class City implements Parcelable {
    private String name;
    private ArrayList<String> images;
    private long longitude;
    private long latitude;


    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel parcel) {
            String name = parcel.readString();
            ArrayList<String> list = new ArrayList<>();
            parcel.readStringList(list);
            City object = new City(name, list);
            object.longitude = parcel.readLong();
            object.latitude = parcel.readLong();
            return object;
        }

        @Override
        public City[] newArray(int i) {
            return new City[i];
        }
    };

    public City(String name, ArrayList<String> image) {
        this.name = name;
        this.images = image;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeStringList(images);
        parcel.writeLong(longitude);
        parcel.writeLong(latitude);
    }
}
