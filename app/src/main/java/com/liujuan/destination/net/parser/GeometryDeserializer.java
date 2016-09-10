package com.liujuan.destination.net.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.liujuan.destination.dto.Location;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GeometryDeserializer implements JsonDeserializer<Location> {

    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject geometry = json.getAsJsonObject();
        JsonObject location = geometry.getAsJsonObject("location");
        double lat = location.get("lat").getAsDouble();
        double lng = location.get("lng").getAsDouble();

        return new Location(lat, lng);
    }
}
