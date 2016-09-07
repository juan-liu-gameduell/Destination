package com.liujuan.destination.net.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.liujuan.destination.model.PhotoResponse;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotoDeserializer implements JsonDeserializer<PhotoResponse> {
    @Override
    public PhotoResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return readPhotoResponse(json);
    }

    @NonNull
    protected static PhotoResponse readPhotoResponse(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        int height = jsonObject.get("height").getAsInt();
        int width = jsonObject.get("width").getAsInt();
        String reference = jsonObject.get("photo_reference").getAsString();
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=%d&photoreference=" + reference + "&key=AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98";

        return new PhotoResponse(height, width, url);
    }
}
