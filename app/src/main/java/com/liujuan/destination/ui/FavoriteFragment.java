package com.liujuan.destination.ui;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liujuan.destination.R;
import com.liujuan.destination.dto.PhotoResponse;
import com.liujuan.destination.ui.adapter.FavoriteCityAdapter;
import com.liujuan.destination.vo.City;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/11.
 */
public class FavoriteFragment extends Fragment {
    public static final String TAG = "FavoriteFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<City> mCities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mockCities();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> favoriteCities = preferences.getStringSet(CityDetailActivity.KET_FAVORITE_CITIES, new HashSet<String>());
        mCities = new ArrayList<>();
//        mCities.add
        Log.i(TAG, favoriteCities.size() + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.destination_city_favorite_recyclerview);
        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        Configuration config = getResources().getConfiguration();
        int SPAN_COUNT = 2;
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT = 3;
        }
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, 40, true));
        mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        mAdapter = new FavoriteCityAdapter(mCities, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void mockCities() {
        mCities = new ArrayList<>();

        List<PhotoResponse> images = new ArrayList<>();
        images.add(new PhotoResponse(500, 500, "http://www.planetware.com/photos-large/D/east-berlin-former-0.jpg"));
        City berlin = new City("Berlin");
        berlin.setImages(images);
        mCities.add(berlin);
    }
}
