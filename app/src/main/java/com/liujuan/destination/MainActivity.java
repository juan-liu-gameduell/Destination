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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

        ArrayList<String> images = new ArrayList<>();
        images.add("http://www.planetware.com/photos-large/D/east-berlin-former-0.jpg");
        mCities.add(new City("Berlin", images));

        ArrayList<String> images1 = new ArrayList<>();
        images1.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("Hamburger", images1));

        ArrayList<String> images2 = new ArrayList<>();
        images2.add("http://www.telegraph.co.uk/content/dam/Travel/Destinations/Asia/China/Beijing/Beijing-cityguide-statue-xlarge.jpg");
        mCities.add(new City("Beijing", images2));

        ArrayList<String> images3 = new ArrayList<>();
        images3.add("https://media.timeout.com/images/100644443/image.jpg");
        mCities.add(new City("London", images3));

        ArrayList<String> images4 = new ArrayList<>();
        images4.add("https://www.burgessyachts.com/media/adminforms/locations/t/o/tokyo_1_1.jpg");
        mCities.add(new City("Tokyo", images4));

        ArrayList<String> images5 = new ArrayList<>();
        images5.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("New York", images5));

        ArrayList<String> images6 = new ArrayList<>();
        images6.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("Sydney", images6));

        ArrayList<String> images7 = new ArrayList<>();
        images7.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("Washington", images7));

        ArrayList<String> images8 = new ArrayList<>();
        images8.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("Barcelona", images8));

        ArrayList<String> images9 = new ArrayList<>();
        images9.add("http://www.eia-jc.org/fileadmin/BILDER/HAMBURG/Hamburger_Rathaus_Luftperspektive.jpg");
        mCities.add(new City("Paris", images9));
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
        Log.i("----", position + "");
    }


}
