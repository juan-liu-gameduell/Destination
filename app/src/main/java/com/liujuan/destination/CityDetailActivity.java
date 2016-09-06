package com.liujuan.destination;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.liujuan.destination.adapter.CityAdapter;
import com.liujuan.destination.adapter.CustomGalleryPagerAdapter;
import com.liujuan.destination.model.City;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CityDetailActivity extends AppCompatActivity {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final String CURRENT_CITY = "currentCity";
    public static final String CURRENT_FAVORITE_STATE = "currentFavoriteState";
    public static final int WHAT_UPDATE_GALLERY = 0;
    private TextView mCityName;
    private boolean isFavorite = false;
    private City mCurrentCity;
    private Handler mHandler;
    private ViewPager mGallery;
    private boolean isPaused;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_details);
        setupToolBar();
        setToolBarUpButton();
        mCityName = (TextView) findViewById(R.id.city_details_name);
        if (savedInstanceState != null) {
            mCurrentCity = savedInstanceState.getParcelable(CURRENT_CITY);
            isFavorite = savedInstanceState.getBoolean(CURRENT_FAVORITE_STATE);
        } else {
            if (getIntent().hasExtra(CityAdapter.EXTRA_CITY)) {
                mCurrentCity = getIntent().getParcelableExtra(CityAdapter.EXTRA_CITY);
            } else {
                callPlaceAutocompleteActivityIntent();
            }
        }
        mHandler = new Handler();
        updateUI();
        setupAutoScroll();
    }

    private void autoScrollGallery() {
        int index = mGallery.getCurrentItem();
        if (index < mGallery.getAdapter().getCount() - 1) {
            index++;
        } else {
            index = 0;
        }
        mGallery.setCurrentItem(index, true);
    }

    private void updateUI() {
        if (mCurrentCity != null) {
            setCityName(mCurrentCity.getName());
            setGallery(mockACity("Beijing").getImages());
        }
    }

    private void setGallery(ArrayList<String> imageUrls) {
        mGallery = (ViewPager) findViewById(R.id.city_details_gallery);
        mGallery.setAdapter(new CustomGalleryPagerAdapter(this, imageUrls));
    }

    private void setupAutoScroll() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    autoScrollGallery();
                }
                if (!CityDetailActivity.this.isFinishing()) {
                    mHandler.postDelayed(mRunnable, 1000);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    private City mockACity(String name) {
        ArrayList<String> images = new ArrayList<>();
        images.add("http://www.planetware.com/photos-large/D/east-berlin-former-0.jpg");
        images.add("http://www.telegraph.co.uk/content/dam/Travel/Destinations/Asia/China/Beijing/Beijing-cityguide-statue-xlarge.jpg");
        images.add("https://media.timeout.com/images/100644443/image.jpg");
        images.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        images.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        return new City(name, images);
    }

    private void setToolBarUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setupToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.city_details_toolbar));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_CITY, mCurrentCity);
        outState.putBoolean(CURRENT_FAVORITE_STATE, isFavorite);
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_city_details, menu);
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
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                isFavorite = !isFavorite;
                if (isFavorite) {
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                } else {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
            Log.i("++++++", e.toString());
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mCurrentCity = mockACity(place.getName().toString());
                updateUI();
            } else {
                finish();
            }
        }
    }

    private void setCityName(String name) {
        getSupportActionBar().setTitle(name);
        mCityName.setText(name);
    }
}