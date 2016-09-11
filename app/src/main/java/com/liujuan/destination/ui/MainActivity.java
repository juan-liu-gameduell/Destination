package com.liujuan.destination.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.liujuan.destination.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String KEY_CURRENT_DRAWER_ITEM_INDEX = "drawerItemIndex";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolBar;
    private DrawerManager mDrawerManager;

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
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        } else {
            int index = savedInstanceState.getInt(KEY_CURRENT_DRAWER_ITEM_INDEX);
            mDrawerManager.setCurrentItemIndex(index);
            setTitle(mDrawerManager.getCurrentTitle());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_DRAWER_ITEM_INDEX, mDrawerManager.getCurrentIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        Log.i(TAG, "select postion is:" + position);

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
        Fragment fragment = new FavoriteFragment();
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
