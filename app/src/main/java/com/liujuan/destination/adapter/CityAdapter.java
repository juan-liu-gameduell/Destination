package com.liujuan.destination.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujuan.destination.CityDetailActivity;
import com.liujuan.destination.R;
import com.liujuan.destination.model.City;
import com.liujuan.destination.model.PhotoResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    public static final String EXTRA_CITY = "city";
    private ArrayList<City> mCities;

    public CityAdapter(ArrayList<City> cityiesData) {
        mCities = cityiesData;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_city_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        final City city = mCities.get(position);
        holder.cityName.setText(city.getName());
        final Context context = holder.cityImage.getContext();
        float px = 160 * context.getResources().getDisplayMetrics().density;
        int pixel = (int) px;
        List<PhotoResponse> images = city.getImages();
        if (images != null && !images.isEmpty()) {
            Picasso.with(context).load(images.get(0).getPhotoUrl()).resize(pixel, pixel).centerCrop().into(holder.cityImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CityDetailActivity.class);
                intent.putExtra(EXTRA_CITY, city);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
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
