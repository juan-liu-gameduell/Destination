package com.liujuan.destination.net.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liujuan.destination.model.Location;
import com.liujuan.destination.model.PhotoResponse;
import com.liujuan.destination.model.PlaceResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/9/9.
 */
public class MyGsonFactory implements Converter<ResponseBody, PlaceResponse> {
    @Override
    public PlaceResponse convert(ResponseBody value) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Location.class, new GeometryDeserializer())
                .registerTypeAdapter(PhotoResponse.class, new PhotoDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(value.string(), PlaceResponse.class);
    }
}
