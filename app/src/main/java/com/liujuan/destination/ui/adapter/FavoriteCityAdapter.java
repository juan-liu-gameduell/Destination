package com.liujuan.destination.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liujuan.destination.R;
import com.liujuan.destination.ui.CityDetailActivity;
import com.liujuan.destination.vo.City;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/4.
 */
public class FavoriteCityAdapter extends RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder> {

    public static final String EXTRA_CITY = "city";
    private ArrayList<City> mCities;

    public FavoriteCityAdapter(ArrayList<City> citiesData, Context context) {
        mCities = citiesData;
    }

    @Override
    public FavoriteCityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_city_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteCityAdapter.ViewHolder holder, int position) {
        final City city = mCities.get(position);
        holder.cityName.setText(city.getName());
        final Context context = holder.cityName.getContext();


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
        private TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.favorite_city_name);
        }
    }
}
