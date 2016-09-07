package com.liujuan.destination.parser;

import com.google.gson.Gson;
import com.liujuan.destination.model.PlacePhoto;
import com.liujuan.destination.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PointOfInterestJsonParser {
    public static final String NEXT_PAGE_TOKEN = "next_page_token";
    public static final String RESULTS = "results";
    public static final String NAME = "name";
    public static final String VICINITY = "vicinity";
    public static final String ID = "id";
    public static final String RATING = "rating";
    public static final String PHOTOS = "photos";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String HTML_ATTRIBUTIONS = "html_attributions";
    Map<String, Object> rootMap;
    String jsonString;

    public PointOfInterestJsonParser(String jsonString) {
        this.jsonString = jsonString;
        parse(jsonString);
    }

    private void parse(String json) {
        rootMap = new Gson().fromJson(json, Map.class);
    }

    public String getNextPageToken() {
        return (String) rootMap.get(NEXT_PAGE_TOKEN);
    }

    public List<PointOfInterest> getPointsOfInterest() {
        List<Map<String, Object>> results = (List<Map<String, Object>>) rootMap.get(RESULTS);
        List<PointOfInterest> list = new ArrayList<>();
        for (Map<String, Object> onePlace : results) {
            String name = (String) onePlace.get(NAME);
            String address = (String) onePlace.get(VICINITY);
            String id = (String) onePlace.get(ID);
            float rating = ((Double) onePlace.get(RATING)).floatValue();

            List<Map<String, Object>> photos = (List<Map<String, Object>>) onePlace.get(PHOTOS);
            PlacePhoto[] photoArrays = new PlacePhoto[photos.size()];
            for (int i = 0; i < photos.size(); i++) {
                Map<String, Object> photo = photos.get(i);
                int height = ((Double) photo.get(HEIGHT)).intValue();
                int width = ((Double) photo.get(WIDTH)).intValue();
                String url = ((List<String>) photo.get(HTML_ATTRIBUTIONS)).get(0);
                photoArrays[i] = new PlacePhoto(height, width, url);
            }

            list.add(new PointOfInterest(name, address, rating, photoArrays, id));


        }
        return list;
    }
}
