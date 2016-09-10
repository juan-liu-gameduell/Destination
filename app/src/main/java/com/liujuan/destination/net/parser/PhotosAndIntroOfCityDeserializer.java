package com.liujuan.destination.net.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.liujuan.destination.dto.PhotoResponse;
import com.liujuan.destination.dto.PhotosAndIntroOfCityResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PhotosAndIntroOfCityDeserializer implements JsonDeserializer<PhotosAndIntroOfCityResponse> {
    @Override
    public PhotosAndIntroOfCityResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (!jsonObject.get("status").getAsString().equals("OK")) {
            return PhotosAndIntroOfCityResponse.createError(jsonObject.get("status").getAsString(),
                    jsonObject.get("error_message").getAsString());
        }
        JsonObject result = jsonObject.get("result").getAsJsonObject();

        List<PhotoResponse> realPhotoUrls = null;
        String intro = null;
        if (result.has("url")) {
            intro = result.get("url").getAsString();
        }

        if (result.has("photos")) {
            JsonArray photos = result.get("photos").getAsJsonArray();
            realPhotoUrls = new ArrayList<>();
            for (JsonElement element : photos) {
                realPhotoUrls.add(PhotoDeserializer.readPhotoResponse(element));
            }
        }

        PhotosAndIntroOfCityResponse photosOfCityResponse = PhotosAndIntroOfCityResponse.create();
        photosOfCityResponse.setPhotos(realPhotoUrls);
        photosOfCityResponse.setIntroductionUrl(intro);
        return photosOfCityResponse;
    }
}
