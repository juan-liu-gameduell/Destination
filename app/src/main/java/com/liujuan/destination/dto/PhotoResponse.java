package com.liujuan.destination.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotoResponse implements Parcelable {
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    private String photoUrl;

    public static final Creator<PhotoResponse> CREATOR = new Creator<PhotoResponse>() {
        @Override
        public PhotoResponse createFromParcel(Parcel parcel) {
            int height = parcel.readInt();
            int width = parcel.readInt();
            String url = parcel.readString();
            PhotoResponse object = new PhotoResponse(height, width, url);
            return object;
        }

        @Override
        public PhotoResponse[] newArray(int i) {
            return new PhotoResponse[i];
        }
    };


    public PhotoResponse(int height, int width, String photoUrl) {
        this.height = height;
        this.width = width;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "height=" + height +
                ", width=" + width +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(photoUrl);
    }
}
