package com.liujuan.destination.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujuan.destination.R;
import com.liujuan.destination.model.InterestResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

    private List<InterestResponse> interests;

    public InterestsAdapter() {
    }

    public void setInterests(List<InterestResponse> interests) {
        this.interests = interests;
    }

    @Override
    public InterestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_city_details_interest_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestsAdapter.ViewHolder holder, int position) {
        final InterestResponse interest = interests.get(position);
        final Context context = holder.interestName.getContext();
        holder.interestName.setText(interest.getName());
        holder.interestAddress.setText(interest.getAddress());
        holder.interestRating.setText("Rating: " + interest.getRating());
        if (interest.getPhotos() != null && !interest.getPhotos().isEmpty()) {
            String imageUrl = String.format(interest.getPhotos().get(0).getPhotoUrl(), holder.interestImage.getWidth());
            Picasso.with(context).load(imageUrl).fit().centerCrop().into(holder.interestImage);
        }

    }

    @Override
    public int getItemCount() {
        return interests == null ? 0 : interests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView interestImage;
        private TextView interestName;
        private TextView interestAddress;
        private TextView interestRating;

        public ViewHolder(View itemView) {
            super(itemView);
            interestImage = (ImageView) itemView.findViewById(R.id.interest_image);
            interestName = (TextView) itemView.findViewById(R.id.interest_name);
            interestAddress = (TextView) itemView.findViewById(R.id.interest_address);
            interestRating = (TextView) itemView.findViewById(R.id.interest_rating);
        }
    }
}
