package com.liujuan.destination.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class City implements Parcelable {
    private String name;
    private List<PhotoResponse> images;
    private double longitude;
    private double latitude;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel parcel) {
            String name = parcel.readString();
            List<PhotoResponse> response = parcel.createTypedArrayList(PhotoResponse.CREATOR);
            City object = new City(name);
            object.setLongitude(parcel.readDouble());
            object.setLatitude(parcel.readDouble());
            object.setId(parcel.readString());
            object.setImages(response);
            return object;
        }

        @Override
        public City[] newArray(int i) {
            return new City[i];
        }
    };

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhotoResponse> getImages() {
        return images;
    }

    public void setImages(List<PhotoResponse> images) {
        this.images = images;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeTypedList(images);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeString(id);
    }
}
