package com.liujuan.destination.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.liujuan.destination.model.City;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CustomGalleryPagerAdapter extends PagerAdapter {

    private City mCity;
    private Context mContext;

    public CustomGalleryPagerAdapter(Context context, City city) {
        this.mContext = context;
        this.mCity = city;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
