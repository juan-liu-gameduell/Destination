package com.liujuan.destination.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liujuan.destination.R;
import com.liujuan.destination.dto.PhotoResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CustomGalleryPagerAdapter extends PagerAdapter {

    private List<PhotoResponse> mImages;
    private Context mContext;

    public CustomGalleryPagerAdapter(Context context) {
        this.mContext = context;
    }

    public void setImages(List<PhotoResponse> images) {
        mImages = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.activity_city_details_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.pager_item_image);
        PhotoResponse photoResponse = mImages.get(position);
        int max = Math.min(photoResponse.getWidth(), photoResponse.getHeight());
        String photoUrl = String.format(mImages.get(position).getPhotoUrl(), max);
        Picasso.with(mContext).load(photoUrl).placeholder(R.drawable.loading).error(R.drawable.item_error)
                .fit().into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
}
