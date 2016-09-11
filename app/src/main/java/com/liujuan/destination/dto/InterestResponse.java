package com.liujuan.destination.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class InterestResponse implements Parcelable {
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

    public static final Creator<InterestResponse> CREATOR = new Creator<InterestResponse>() {
        @Override
        public InterestResponse createFromParcel(Parcel parcel) {
            InterestResponse interestResponse = new InterestResponse();
            interestResponse.id = parcel.readString();
            interestResponse.name = parcel.readString();
            interestResponse.rating = parcel.readDouble();
            interestResponse.address = parcel.readString();
            interestResponse.photos = parcel.createTypedArrayList(PhotoResponse.CREATOR);
            interestResponse.location = parcel.readParcelable(Location.CREATOR.getClass().getClassLoader());
            return interestResponse;
        }

        @Override
        public InterestResponse[] newArray(int i) {
            return new InterestResponse[i];
        }
    };

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

    public Location getLocation() {
        return location;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(rating);
        dest.writeString(address);
        dest.writeTypedList(photos);
        dest.writeParcelable(location, flags);
    }
}
