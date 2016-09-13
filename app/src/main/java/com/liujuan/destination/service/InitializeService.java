package com.liujuan.destination.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.liujuan.destination.R;
import com.liujuan.destination.dto.CityResponse;
import com.liujuan.destination.dto.InterestResponse;
import com.liujuan.destination.dto.PlaceResponse;
import com.liujuan.destination.net.SearchService;
import com.liujuan.destination.utl.ReaderAndWriterCityUtil;
import com.liujuan.destination.vo.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2016/9/12.
 */
public class InitializeService extends Service {
    public static final String ACTION_PREPARE_DEFAULT_CITIES = "PrepareDefaultAndFavoriteCities";
    private static final String TAG = "InitializeService";
    public static final String ACTION_DEFAULT_CITIES_READY = "DefaultCitiesReady";
    public static final String EXTRA_HOT_CITIES = "hotCities";
    private ServiceHandler mServiceHandler;
    private String[] defaultCityIds;

    @Override
    public void onCreate() {
        defaultCityIds = getResources().getStringArray(R.array.city_ids);
        HandlerThread thread = new HandlerThread("downloading hot cities", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceHandler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(ACTION_PREPARE_DEFAULT_CITIES)) {
            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = startId;
            mServiceHandler.sendMessage(msg);
        } else {
            stopSelf(startId);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            ArrayList<City> hotCities = readOrDownloadAndSaveCityDetails();
            sendHotCityNotification(hotCities);
            stopSelf(msg.arg1);
        }
    }

    private void sendHotCityNotification(ArrayList<City> cities) {
        Intent intent = new Intent(ACTION_DEFAULT_CITIES_READY);
        intent.putParcelableArrayListExtra(EXTRA_HOT_CITIES, cities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private ArrayList<City> readOrDownloadAndSaveCityDetails() {
        SearchService searchService = SearchService.Factory.create();
        ArrayList<City> cities = new ArrayList<>(defaultCityIds.length);
        for (String id : defaultCityIds) {
            try {
                City city = ReaderAndWriterCityUtil.readCityFromFile(id, this);
                if (city == null) {
                    city = downloadCityDetails(searchService, id);
                    city.setInterests(downloadInterests(searchService, city));
                    if (ReaderAndWriterCityUtil.saveACity(city, this)) {
                        cities.add(city);
                    }
                } else {
                    cities.add(city);
                }
            } catch (Exception e) {
                Log.i(TAG, "error happened when reading cities from file or downloading cities: " + e.getMessage());
            }
        }
        return cities;
    }

    @NonNull
    private City downloadCityDetails(SearchService searchService, String id) throws java.io.IOException {
        Log.i(TAG, "Downloading a city: " + id + " from internet");
        Call<CityResponse> cityResponseCall = searchService.queryDetailsOfCity(id);
        CityResponse cityResponse = cityResponseCall.execute().body();
        City city = new City(cityResponse.getResultResponse().getName(), id);
        city.setLatitude(cityResponse.getResultResponse().getLocation().getLat());
        city.setLongitude(cityResponse.getResultResponse().getLocation().getLng());
        city.setImages(cityResponse.getResultResponse().getPhotoResponseList());
        return city;
    }

    private List<InterestResponse> downloadInterests(SearchService searchService, City city) throws java.io.IOException {
        Call<PlaceResponse> interestResponseCall = searchService.searchNearbyPointsOfInterest(
                city.getLatitude() + "," + city.getLongitude(), 10000
        );
        PlaceResponse response = interestResponseCall.execute().body();
        if (response == null || response.hasError()) {
            return null;
        } else {
            List<InterestResponse> list = response.getInterests();
            Collections.sort(list);
            return list;
        }
    }
}
