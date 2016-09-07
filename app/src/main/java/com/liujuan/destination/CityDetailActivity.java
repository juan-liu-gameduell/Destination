package com.liujuan.destination;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.liujuan.destination.model.PointOfInterest;
import com.liujuan.destination.parser.PointOfInterestJsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CityDetailActivity extends AppCompatActivity {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final String CURRENT_CITY = "currentCity";
    public static final String KET_FAVORITE_CITIES = "Favorite cities";
    private static final String TAG = "CityDetailActivity";
    private TextView mCityNameView;
    private City mCurrentCity;
    private Handler mHandler;
    private ViewPager mGallery;
    private boolean isPaused;
    private Runnable mRunnable;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_details);
        setToolBar();
        setToolBarUpButton();
        mCityNameView = (TextView) findViewById(R.id.city_details_name);
        if (savedInstanceState != null) {
            mCurrentCity = savedInstanceState.getParcelable(CURRENT_CITY);
        } else {
            if (getIntent().hasExtra(CityAdapter.EXTRA_CITY)) {
                mCurrentCity = getIntent().getParcelableExtra(CityAdapter.EXTRA_CITY);
            } else {
                callPlaceAutocompleteActivityIntent();
            }
        }

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mHandler = new Handler();
        setCityNameAndImages();
        setAutoScroll();
        loadDataFromInterNet("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.503186,-0.126446&radius=5000&type=point_of_interest&key=" + "AIzaSyCIxYdbwTn7InxBxJw0fv5lHj_QCdiVD98");
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
        int index = mGallery.getCurrentItem();
        if (index < mGallery.getAdapter().getCount() - 1) {
            index++;
        } else {
            index = 0;
        }
        mGallery.setCurrentItem(index, true);
    }

    private void setCityNameAndImages() {
        if (mCurrentCity != null) {
            setCityName(mCurrentCity.getName());
            setGallery(mockACity("Beijing").getImages());
        }
    }

    private void setGallery(ArrayList<String> imageUrls) {
        mGallery = (ViewPager) findViewById(R.id.city_details_gallery);
        mGallery.setAdapter(new CustomGalleryPagerAdapter(this, imageUrls));
    }

    private void setAutoScroll() {
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


    private void setToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.city_details_toolbar));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_CITY, mCurrentCity);
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
            Log.i("++++++", e.toString());
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mCurrentCity = mockACity(place.getName().toString());
                setCityNameAndImages();
                invalidateOptionsMenu();
            } else {
                finish();
            }
        }
    }

    private void setCityName(String name) {
        getSupportActionBar().setTitle(name);
        mCityNameView.setText(name);
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public void loadDataFromInterNet(String url) {
        if (isOnline()) {
            new DownloadTask().execute(url);
        } else {
            Log.i("---------", "no network");
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return downloadUrl(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            mCityNameView.setText(s);
            List<PointOfInterest> list = new PointOfInterestJsonParser(s).getPointsOfInterest();
            for (PointOfInterest p : list) {
                Log.i("======", p.toString());
            }
        }
    }

    private String downloadUrl(String myUrl) {
        InputStream in = null;
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            Log.d("------", "the response is : " + responseCode);

            in = connection.getInputStream();
            String contentAsString = readIn(in, 10000);
            return contentAsString;
        } catch (Exception e) {
            Log.e(TAG, "error happened when downloading URL:" + myUrl + ", error:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String readIn(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }


}