package com.liujuan.destination.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujuan.destination.R;
import com.liujuan.destination.dto.PhotoResponse;
import com.liujuan.destination.ui.CityDetailActivity;
import com.liujuan.destination.utl.LayoutUtil;
import com.liujuan.destination.vo.City;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class HotCityAdapter extends RecyclerView.Adapter<HotCityAdapter.ViewHolder> {

    public static final String EXTRA_CITY = "city";
    private List<City> mCities;
    private int photoWidth;

    public HotCityAdapter(List<City> citiesData, Context context) {
        mCities = citiesData;
        photoWidth = (int) LayoutUtil.convertDpToPixel(context.getResources().getDimension(R.dimen.destination_city_recyclerview_item_width), context);
    }

    public void setCities(List<City> cities) {
        mCities = cities;
    }

    @Override
    public HotCityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_city_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotCityAdapter.ViewHolder holder, int position) {
        final City city = mCities.get(position);
        holder.cityName.setText(city.getName());
        final Context context = holder.cityImage.getContext();

        List<PhotoResponse> images = city.getImages();
        if (images != null && !images.isEmpty()) {
            String imageUrl = String.format(images.get(0).getPhotoUrl(), photoWidth);
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.loading).error(R.drawable.item_error).fit().into(holder.cityImage);
        }else {
            holder.cityImage.setImageResource(R.drawable.item_error);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CityDetailActivity.class);
                intent.putExtra(EXTRA_CITY, (Parcelable) city);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities == null ? 0 : mCities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cityImage;
        private TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityImage = (ImageView) itemView.findViewById(R.id.destination_city_image);
            cityName = (TextView) itemView.findViewById(R.id.destination_city_name);
        }
    }
}
