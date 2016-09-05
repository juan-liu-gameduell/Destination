package com.liujuan.destination;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.liujuan.destination.adapter.CityAdapter;
import com.liujuan.destination.model.City;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<City> mCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mockCities();
        mRecyclerView = (RecyclerView) findViewById(R.id.destination_city_recyclerview);


        setupRecyclerView();
        setupToolBar();


    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        Configuration config = getResources().getConfiguration();
        int SPAN_COUNT = 2;
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT = 3;
        }
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, 50, true));
        mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mAdapter = new CityAdapter(mCities);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
            startCityDetailActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCityDetailActivity() {
        Intent intent = new Intent(this, CityDetailActivity.class);
        startActivity(intent);

    }

}
