package com.liujuan.destination.ui;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.liujuan.destination.R;
import com.liujuan.destination.ui.adapter.FavoriteCityAdapter;

/**
 * Created by Administrator on 2016/9/11.
 */
public class FavoriteFragment extends Fragment {
    public static final String TAG = "FavoriteFragment";
    private RecyclerView mRecyclerView;
    private FavoriteCityAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private MainActivity mMainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        rootView.setTag(TAG);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_favorite_cities);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.destination_city_favorite_recyclerview);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        updateAdapter();
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
        mAdapter = new FavoriteCityAdapter(((MainActivity) getActivity()).getFavoriteCities(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void updateVisibility(boolean recyclerViewVisible) {
        if (recyclerViewVisible) {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public void updateAdapter() {
        if (mAdapter != null) {
            updateVisibility(mMainActivity.getFavoriteCities() != null);
            mAdapter.setCities(((MainActivity) getActivity()).getFavoriteCities());
            mAdapter.notifyDataSetChanged();
        }
    }
}
