package com.liujuan.destination.net;

import com.liujuan.destination.model.PlaceResponse;
import com.liujuan.destination.net.parser.MyGsonFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    String API_KEY = "AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98";

    @GET("maps/api/place/nearbysearch/json?rankby=prominence&types=park|church|city_hall|zoo|museum|movie_theater|local_government_office|library|amusement_park|aquarium|art_gallery|hindu_temple|stadium&sensor=false&key=" + API_KEY)
    Call<PlaceResponse> searchNearbyPointsOfInterest(@Query("location") String location,
                                                     @Query("radius") int radius);


    class Factory {
        public static SearchService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(new Converter.Factory() {
                        @Override
                        public Converter<ResponseBody, PlaceResponse> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                            return new MyGsonFactory();
                        }
                    })
                    .build();
            return retrofit.create(SearchService.class);
        }

    }
}
