package com.liujuan.destination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.liujuan.destination.model.City;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<City> mCities;
    private PlaceAutocompleteFragment citySearchFragment;
    private View citySearchCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mockCities();
        mRecyclerView = (RecyclerView) findViewById(R.id.destination_city_recyclerview);
        citySearchCardView = findViewById(R.id.city_search_cardView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new CityAdapter(mCities);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setupToolBar();
        setupPlaceSelectListener();
    }

    private void setupToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void mockCities() {
        mCities = new ArrayList<>();
        mCities.add(new City("Berlin", "http://www.planetware.com/photos-large/D/east-berlin-former-0.jpg"));
        mCities.add(new City("Hamburger", "http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg"));
        mCities.add(new City("Beijing", "http://www.telegraph.co.uk/content/dam/Travel/Destinations/Asia/China/Beijing/Beijing-cityguide-statue-xlarge.jpg"));
        mCities.add(new City("London", "https://media.timeout.com/images/100644443/image.jpg"));
        mCities.add(new City("Tokyo", "https://www.burgessyachts.com/media/adminforms/locations/t/o/tokyo_1_1.jpg"));
        mCities.add(new City("New York", "https://www.omnihotels.com/-/media/images/hotels/nycber/destinations/nyc-aerial-skyline.jpg?h=660&la=en&w=1170"));
        mCities.add(new City("Sydney", "https://media.timeout.com/images/100644443/image.jpg"));
        mCities.add(new City("Washington", "https://media.timeout.com/images/100644443/image.jpg"));
        mCities.add(new City("Paris", "https://media.timeout.com/images/100644443/image.jpg"));
        mCities.add(new City("Barcelona", "https://media.timeout.com/images/100644443/image.jpg"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionbar_search) {
            showCitySearchFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (citySearchCardView.getVisibility() == View.VISIBLE) {
            hideCitySearchFragment();
        } else {
            super.onBackPressed();
        }
    }

    private void showCitySearchFragment() {
        citySearchCardView.setVisibility(View.VISIBLE);
    }

    private void hideCitySearchFragment() {
        citySearchCardView.setVisibility(View.GONE);
    }

    private void setupPlaceSelectListener() {
        citySearchFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        citySearchFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                hideCitySearchFragment();
            }

            @Override
            public void onError(Status status) {

                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

}
