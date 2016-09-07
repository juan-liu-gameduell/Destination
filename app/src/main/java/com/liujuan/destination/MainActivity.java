package com.liujuan.destination;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liujuan.destination.adapter.CityAdapter;
import com.liujuan.destination.model.City;
import com.liujuan.destination.model.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<City> mCities;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mockCities();
        mRecyclerView = (RecyclerView) findViewById(R.id.destination_city_recyclerview);


        setupRecyclerView();
        setupToolBar();

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, (Toolbar) findViewById(R.id.toolbar),
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void mockCities() {
        mCities = new ArrayList<>();

        List<PhotoResponse> images = new ArrayList<>();
        images.add(new PhotoResponse(500, 500, "http://www.planetware.com/photos-large/D/east-berlin-former-0.jpg"));
        City berlin = new City("Berlin");
        berlin.setImages(images);
        mCities.add(berlin);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Log.i(TAG, "select postion is:" + position);
    }

}
