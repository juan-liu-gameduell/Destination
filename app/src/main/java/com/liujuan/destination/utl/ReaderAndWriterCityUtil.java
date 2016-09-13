package com.liujuan.destination.utl;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.liujuan.destination.ui.CityDetailActivity;
import com.liujuan.destination.vo.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/12.
 */
public class ReaderAndWriterCityUtil {

    public static final String TAG = "ReadeAndWritFromFile";

    public static boolean isCitySavedInFile(String id, Context context) {
        return new File(context.getFilesDir(), id).exists();
    }

    public static City readCityFromFile(String id, Context context) {
        Log.i(TAG, "Reading a city: " + id + " from file");
        ObjectInputStream objectInputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(context.getFilesDir(), id));
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (City) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "error in reading from file:" + e.getMessage());
        }
        return null;
    }

    public static void saveCities(List<City> cities, Context context) {
        for (City city : cities) {
            saveACity(city, context);
        }
    }

    public static boolean saveACity(City city, Context context) {
        Log.i(TAG, "Saving a city: " + city.getId() + " into file");
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), city.getId()));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(city);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "error happened when serializing city to file:" + e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static ArrayList<City> readFavoriteCities(Context context) {
        Set<String> favoriteCityIds = PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(CityDetailActivity.KET_FAVORITE_CITIES, new HashSet<String>());
        ArrayList<City> cities = new ArrayList<>();
        for (String id : favoriteCityIds) {
            City city = readCityFromFile(id, context);
            if (city != null) {
                cities.add(city);
            }
        }
        return cities;
    }

    static void mergeFavoriteCities(ArrayList<City> oldFavoriteCities, Set<String> favoriteCityIds) {
        Set<String> oldIds = new HashSet<>();
        for (int i = oldFavoriteCities.size() - 1; i >= 0; i--) {
            City city = oldFavoriteCities.get(i);
            oldIds.add(city.getId());
            if (!favoriteCityIds.contains(city.getId())) {
                oldFavoriteCities.remove(city);
            }
        }

        favoriteCityIds.removeAll(oldIds);
    }
}
