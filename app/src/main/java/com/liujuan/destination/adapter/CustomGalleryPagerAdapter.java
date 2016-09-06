package com.liujuan.destination.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liujuan.destination.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CustomGalleryPagerAdapter extends PagerAdapter {

    private ArrayList<String> mImages;
    private Context mContext;

    public CustomGalleryPagerAdapter(Context context, ArrayList<String> urls) {
        this.mContext = context;
        this.mImages = urls;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.activity_city_details_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.pager_item_image);

        Picasso.with(mContext).load(mImages.get(position)).fit().centerCrop().into(imageView);
        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
}
