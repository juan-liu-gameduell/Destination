package com.liujuan.destination.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.liujuan.destination.dto.InterestResponse;
import com.liujuan.destination.dto.PhotoResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class City implements Parcelable, Serializable {
    static final long serialVersionUID = 1L;
    private String name;
    private List<PhotoResponse> images;
    private double longitude;
    private double latitude;
    private String id;
    private String url;
    private List<InterestResponse> interests;

    public List<InterestResponse> getInterests() {
        return interests;
    }

    public void setInterests(List<InterestResponse> interests) {
        this.interests = interests;
    }

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
            City object = new City(name, parcel.readString());
            object.setLongitude(parcel.readDouble());
            object.setLatitude(parcel.readDouble());
            List<PhotoResponse> response = parcel.createTypedArrayList(PhotoResponse.CREATOR);
            object.setImages(response);
            object.setInterests(parcel.createTypedArrayList(InterestResponse.CREATOR));
            return object;
        }

        @Override
        public City[] newArray(int i) {
            return new City[i];
        }
    };

    public City(String name, String id) {
        this.name = name;
        this.id = id;
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
        parcel.writeString(id);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeTypedList(images);
        parcel.writeTypedList(interests);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
