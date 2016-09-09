package com.liujuan.destination.net;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

//    String API_KEY = "AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98";
    String API_KEY = "AIzaSyAZWIfu7DblfR0UljR3GzP-PQNfrW8NMgc";

    @GET("maps/api/place/nearbysearch/json?rankby=prominence&types=park|church|city_hall|zoo|museum|movie_theater|local_government_office|library|amusement_park|aquarium|art_gallery|hindu_temple|stadium&sensor=false&key=" + API_KEY)
    Call<ResponseBody> searchNearby(@Query("location") String location,
                                    @Query("radius") int radius,
                                    @Query("type") String type,
                                    @Query("name") String names);

    class Factory {
        public static SearchService create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .client(client)
                    .build();
            return retrofit.create(SearchService.class);
        }

    }
}
