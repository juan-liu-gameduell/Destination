package com.liujuan.destination.net;

import com.liujuan.destination.model.PlaceResponse;
import com.liujuan.destination.net.parser.MyGsonFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

//    String API_KEY = "AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98";
    String API_KEY = "AIzaSyAZWIfu7DblfR0UljR3GzP-PQNfrW8NMgc";

    @GET("maps/api/place/nearbysearch/json?rankby=prominence&types=park|church|city_hall|zoo|museum|movie_theater|local_government_office|library|amusement_park|aquarium|art_gallery|hindu_temple|stadium&sensor=false&key=" + API_KEY)
    Call<PlaceResponse> searchNearbyPointsOfInterest(@Query("location") String location,
                                                     @Query("radius") int radius);


    class Factory {
        public static SearchService create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(new Converter.Factory() {
                        @Override
                        public Converter<ResponseBody, PlaceResponse> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                            return new MyGsonFactory();
                        }
                    })
                    .client(client)
                    .build();
            return retrofit.create(SearchService.class);
        }

    }
}
