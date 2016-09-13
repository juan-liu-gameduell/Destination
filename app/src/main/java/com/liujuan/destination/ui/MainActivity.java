package com.liujuan.destination.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.liujuan.destination.R;
import com.liujuan.destination.service.InitializeService;
import com.liujuan.destination.utl.ReaderAndWriterCityUtil;
import com.liujuan.destination.vo.City;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String KEY_CURRENT_DRAWER_ITEM_INDEX = "drawerItemIndex";
    public static final String KEY_HOT_CITIES = "HotCities";
    public static final String KEY_FAVORITE_CITIES = "FavoriteCities";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolBar;
    private DrawerManager mDrawerManager;
    private ArrayList<City> mHotCities;
    private ArrayList<City> mFavoriteCities;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(InitializeService.ACTION_DEFAULT_CITIES_READY)) {
                mHotCities = intent.getParcelableArrayListExtra(InitializeService.EXTRA_HOT_CITIES);
                MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentByTag(MainFragment.TAG);
                if (mainFragment != null) {
                    mainFragment.updateAdapter();
                }
            }
        }
    };

    private void updateFavoriteFragmentByDataChange() {
        FavoriteFragment favoriteFragment = (FavoriteFragment) getFragmentManager().findFragmentByTag(FavoriteFragment.TAG);
        if (favoriteFragment != null) {
            favoriteFragment.updateAdapter();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mDrawerManager = new DrawerManager(this, findViewById(R.id.left_drawer));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNavDrawerOpen())
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(InitializeService.ACTION_DEFAULT_CITIES_READY);
            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);

            Intent service = new Intent(this, InitializeService.class);
            service.setAction(InitializeService.ACTION_PREPARE_DEFAULT_CITIES);
            startService(service);
        } else {
            int index = savedInstanceState.getInt(KEY_CURRENT_DRAWER_ITEM_INDEX);
            mDrawerManager.setCurrentItemIndex(index);
            setTitle(mDrawerManager.getCurrentTitle());
            mHotCities = savedInstanceState.getParcelableArrayList(KEY_HOT_CITIES);
            mFavoriteCities = savedInstanceState.getParcelableArrayList(KEY_FAVORITE_CITIES);
        }
    }

    public ArrayList<City> getHotCities() {
        return mHotCities;
    }

    public ArrayList<City> getFavoriteCities() {
        return mFavoriteCities;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFavoriteCities = ReaderAndWriterCityUtil.readFavoriteCities(this);
        updateFavoriteFragmentByDataChange();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_DRAWER_ITEM_INDEX, mDrawerManager.getCurrentIndex());
        outState.putParcelableArrayList(KEY_HOT_CITIES, mHotCities);
        outState.putParcelableArrayList(KEY_FAVORITE_CITIES, mFavoriteCities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionbar_search) {
            startCityDetailActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCityDetailActivity() {
        startActivity(new Intent(this, CityDetailActivity.class));
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }


    private boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    void selectItem(final int position) {
        setTitle(mDrawerManager.getCurrentTitle());
        switch (position) {
            case 0:
                showMainFragment();
                break;
            case 1:
                showFavoriteCitiesFragment();
                break;
            default:
                throw new IllegalArgumentException("unsupported position in DrawerLayout List:" + position);
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFavoriteFragment() != null && !isNavDrawerOpen()) {
                returnToHome();
                return true;
            } else if (isNavDrawerOpen()) {
                closeNavDrawer();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private Fragment getFavoriteFragment() {
        return getFragmentManager().findFragmentByTag(FavoriteFragment.TAG);
    }

    private void returnToHome() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        mDrawerManager.setCurrentItemIndex(0);
        setTitle(mDrawerManager.getCurrentTitle());
    }

    private void showFavoriteCitiesFragment() {
        FragmentManager fm = getFragmentManager();
        String tag = FavoriteFragment.TAG;
        FavoriteFragment fragment = new FavoriteFragment();
        fm.beginTransaction().replace(R.id.content_frame, fragment, tag)
                .addToBackStack(null).commit();
    }

    private void showMainFragment() {
        FragmentManager fm = getFragmentManager();
        String tag = MainFragment.TAG;
        Fragment fragment = new MainFragment();
        fm.beginTransaction().replace(R.id.content_frame, fragment, tag).commit();
    }
}
