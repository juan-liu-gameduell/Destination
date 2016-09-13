package com.liujuan.destination.net;

import com.google.gson.GsonBuilder;
import com.liujuan.destination.dto.CityResponse;
import com.liujuan.destination.dto.Location;
import com.liujuan.destination.dto.PhotoResponse;
import com.liujuan.destination.dto.PhotosAndIntroOfCityResponse;
import com.liujuan.destination.dto.PlaceResponse;
import com.liujuan.destination.net.parser.GeometryDeserializer;
import com.liujuan.destination.net.parser.PhotoDeserializer;
import com.liujuan.destination.net.parser.PhotosAndIntroOfCityDeserializer;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    String API_KEY = "AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98";
//    String API_KEY = "AIzaSyAZWIfu7DblfR0UljR3GzP-PQNfrW8NMgc";
//    String API_KEY = "AIzaSyD3ymVLGpr0Q_v7H5dIx0Ef2s2px3DYNFI";

    @GET("maps/api/place/nearbysearch/json?rankby=prominence&types=park|church|city_hall|zoo|museum|movie_theater|local_government_office|library|amusement_park|aquarium|art_gallery|hindu_temple|stadium|synagogue|place_of_worship|mosque|university|cemetery|casino&sensor=false&key=" + API_KEY)
    Call<PlaceResponse> searchNearbyPointsOfInterest(@Query("location") String location,
                                                     @Query("radius") int radius);

    @GET("maps/api/place/details/json?key=" + API_KEY)
    Call<PhotosAndIntroOfCityResponse> searchPhotosOfCity(@Query("placeid") String id);


    @GET("maps/api/place/details/json?key=" + API_KEY)
    Call<CityResponse> queryDetailsOfCity(@Query("placeid") String id);

    class Factory {
        public static SearchService create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(PhotosAndIntroOfCityResponse.class, new PhotosAndIntroOfCityDeserializer())
                    .registerTypeAdapter(Location.class, new GeometryDeserializer())
                    .registerTypeAdapter(PhotoResponse.class, new PhotoDeserializer());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .client(client)
                    .build();
            return retrofit.create(SearchService.class);
        }

    }
}
