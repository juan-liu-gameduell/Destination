package com.liujuan.destination.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Location implements Parcelable {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel parcel) {
            double lat = parcel.readDouble();
            double lng = parcel.readDouble();
            return new Location(lat, lng);
        }

        @Override
        public Location[] newArray(int i) {
            return new Location[i];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
