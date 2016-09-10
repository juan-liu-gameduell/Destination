package com.liujuan.destination.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.liujuan.destination.R;
import com.liujuan.destination.dto.InterestResponse;
import com.liujuan.destination.dto.PhotoResponse;
import com.liujuan.destination.dto.PhotosAndIntroOfCityResponse;
import com.liujuan.destination.dto.PlaceResponse;
import com.liujuan.destination.net.NetClient;
import com.liujuan.destination.net.SearchService;
import com.liujuan.destination.ui.adapter.CityAdapter;
import com.liujuan.destination.ui.adapter.CustomGalleryPagerAdapter;
import com.liujuan.destination.ui.adapter.InterestsAdapter;
import com.liujuan.destination.vo.City;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CityDetailActivity extends AppCompatActivity {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final String CURRENT_CITY = "currentCity";
    public static final String KET_FAVORITE_CITIES = "Favorite cities";
    private static final String TAG = "CityDetailActivity";
    public static final String IS_GALLERY_TOUCHED = "isGalleryTouched";
    private RecyclerView mInterestRecyclerView;
    private City mCurrentCity;
    private Handler mHandler;
    private ViewPager mGallery;
    private boolean isPaused;
    private Runnable mRunnable;
    private SharedPreferences mSharedPreferences;
    private CustomGalleryPagerAdapter mGalleryAdapter;
    private InterestsAdapter mInterestsAdapter;
    private boolean isGalleryTouched;
    private TextView recommendedPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_details);
        setToolBar();
        setToolBarUpButton();
        mGallery = (ViewPager) findViewById(R.id.city_details_gallery);
        recommendedPlaces = (TextView) findViewById(R.id.recommened_place_text);
        mInterestRecyclerView = (RecyclerView) findViewById(R.id.city_details_points_of_interest);
        if (savedInstanceState != null) {
            mCurrentCity = savedInstanceState.getParcelable(CURRENT_CITY);
            isGalleryTouched = savedInstanceState.getBoolean(IS_GALLERY_TOUCHED);
        } else {
            if (getIntent().hasExtra(CityAdapter.EXTRA_CITY)) {
                mCurrentCity = getIntent().getParcelableExtra(CityAdapter.EXTRA_CITY);
            } else {
                callPlaceAutocompleteActivityIntent();
            }
        }

        mGalleryAdapter = new CustomGalleryPagerAdapter(this);
        mGallery.setAdapter(mGalleryAdapter);
        mGallery.setOnTouchListener(createGalleryTouchListener());
        setInterestRecyclerView();
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mHandler = new Handler();
        setRecommendedPlacesVisibility();
        if (mCurrentCity != null) {
            setCityName(mCurrentCity.getName());
            updateGalleryAdapter(mCurrentCity.getImages());
        }
    }

    @NonNull
    private View.OnTouchListener createGalleryTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    isGalleryTouched = true;
                    mHandler.removeCallbacks(mRunnable);
                }
                return false;
            }
        };
    }

    private void setInterestRecyclerView() {
        mInterestRecyclerView.setHasFixedSize(false);
        mInterestsAdapter = new InterestsAdapter(this);
        if (mCurrentCity != null) {
            mInterestsAdapter.setInterests(mCurrentCity.getInterests());
        }
        mInterestRecyclerView.setAdapter(mInterestsAdapter);
        mInterestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private boolean readIsFavorite() {
        boolean isFavorite = false;
        if (mCurrentCity != null && mSharedPreferences.contains(KET_FAVORITE_CITIES)) {
            Set<String> favoriteCities = mSharedPreferences.getStringSet(KET_FAVORITE_CITIES, new HashSet<String>());
            isFavorite = favoriteCities.contains(mCurrentCity.getName());
        }
        return isFavorite;
    }

    private void autoScrollGallery() {
        int count = mGallery.getAdapter().getCount();
        if (count > 0) {
            int index = mGallery.getCurrentItem();
            if (index < count - 1) {
                index++;
            } else {
                index = 0;
            }
            mGallery.setCurrentItem(index, true);
        }
    }

    private void setAutoScroll() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    autoScrollGallery();
                }
                if (!CityDetailActivity.this.isFinishing() && !isGalleryTouched) {
                    mHandler.postDelayed(mRunnable, 2500);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 2500);
    }

    private void setToolBarUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.city_details_toolbar));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_CITY, mCurrentCity);
        outState.putBoolean(IS_GALLERY_TOUCHED, isGalleryTouched);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    protected void onPause() {
        isPaused = true;
        super.onPause();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_city_details, menu);
        boolean isFavorite = readIsFavorite();
        if (isFavorite) {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_black_24dp);
        } else {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_white_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                boolean isFavorite = !readIsFavorite();
                writeFavorite(isFavorite);
                if (isFavorite) {
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                } else {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                }
                return true;
            case R.id.actionbar_search:
                callPlaceAutocompleteActivityIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void writeFavorite(boolean isFavorite) {
        Set<String> favoriteCities = mSharedPreferences.getStringSet(KET_FAVORITE_CITIES, new HashSet<String>());
        if (isFavorite) {
            favoriteCities.add(mCurrentCity.getName());
        } else {
            favoriteCities.remove(mCurrentCity.getName());
        }
        mSharedPreferences.edit().putStringSet(KET_FAVORITE_CITIES, favoriteCities).apply();
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            Log.i(TAG, "google place autocomplete intent error:" + e.getMessage());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mCurrentCity = createCityFromPlace(place);
                setCityName(mCurrentCity.getName());
                invalidateOptionsMenu();
                clearUIContent();
                loadDataFromInterNet(mCurrentCity);
            } else if (mCurrentCity == null) {
                finish();
            }
        }
    }

    private void clearUIContent() {
        setRecommendedPlacesVisibility();
        mInterestsAdapter.setInterests(null);
        mInterestsAdapter.notifyDataSetChanged();
        mGalleryAdapter = new CustomGalleryPagerAdapter(this);
        mGallery.setAdapter(mGalleryAdapter);
    }

    private City createCityFromPlace(Place place) {
        City city = new City(place.getName().toString());
        city.setLatitude(place.getLatLng().latitude);
        city.setLongitude(place.getLatLng().longitude);
        city.setId(place.getId());
        return city;
    }

    private void loadDataFromInterNet(City city) {
        if (NetClient.isOnline(this)) {
            new FetchCityImagesTask().execute(city);
            new FetchPlaceOfInterestTask().execute(city);
        } else {
            Log.i(TAG, "load data from internet");
        }
    }

    private void updateGalleryAdapter(List<PhotoResponse> images) {
        if (images != null && !images.isEmpty()) {
            mGalleryAdapter.setImages(images);
            mGalleryAdapter.notifyDataSetChanged();
            if (!isGalleryTouched) {
                setAutoScroll();
            }
        }
    }

    private class FetchCityImagesTask extends AsyncTask<City, Void, PhotosAndIntroOfCityResponse> {

        @Override
        protected PhotosAndIntroOfCityResponse doInBackground(City... params) {
            Log.i(TAG, "fetching images views of a city");
            City city = params[0];
            SearchService searchService = SearchService.Factory.create();
            Call<PhotosAndIntroOfCityResponse> photosCall = searchService.searchPhotosOfCity(city.getId());
            try {
                return photosCall.execute().body();
            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(PhotosAndIntroOfCityResponse response) {
            if (response != null) {
                mCurrentCity.setImages(response.getPhotos());
                mCurrentCity.setUrl(response.getIntroductionUrl());
                updateGalleryAdapter(response.getPhotos());
            }
        }
    }

    private void setCityName(String name) {
        getSupportActionBar().setTitle(name);
    }

    private class FetchPlaceOfInterestTask extends AsyncTask<City, Void, PlaceResponse> {
        @Override
        protected PlaceResponse doInBackground(City... params) {
            Log.i(TAG, "fetching places of interest of a city");
            City city = params[0];
            String latlng = city.getLatitude() + "," + city.getLongitude();

            SearchService searchService = SearchService.Factory.create();
            Call<PlaceResponse> stringCall = searchService.searchNearbyPointsOfInterest(latlng, 10000);
            try {
                return stringCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(PlaceResponse response) {
            if (response == null || response.hasError()) {
                showErrorDialog(response);
            } else {
                List<InterestResponse> list = response.getInterests();

                Collections.sort(list, new Comparator<InterestResponse>() {
                    @Override
                    public int compare(final InterestResponse lhs, InterestResponse rhs) {
                        //TODO return 1 if rhs should be before lhs
                        //     return -1 if lhs should be before rhs
                        //     return 0 otherwise
                        if (lhs.getRating() > rhs.getRating()) {
                            return -1;
                        } else if (lhs.getRating() < rhs.getRating()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                mCurrentCity.setInterests(list);
                mInterestsAdapter.setInterests(mCurrentCity.getInterests());
                mInterestsAdapter.notifyDataSetChanged();
                setRecommendedPlacesVisibility();
            }
        }
    }

    private void setRecommendedPlacesVisibility() {
        if (mCurrentCity != null && mCurrentCity.getInterests() != null && !mCurrentCity.getInterests().isEmpty()) {
            recommendedPlaces.setVisibility(View.VISIBLE);
        } else {
            recommendedPlaces.setVisibility(View.INVISIBLE);
        }
    }

    private void showErrorDialog(PlaceResponse response) {
        if (response == null) {
            SimpleDialog.showDialog(getFragmentManager(), getString(R.string.error_title), getString(R.string.error_message));
        } else {
            SimpleDialog.showDialog(getFragmentManager(), getString(R.string.error_title), response.getErrorMessage());
        }
    }
}