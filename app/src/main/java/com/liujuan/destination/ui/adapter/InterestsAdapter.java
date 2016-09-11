package com.liujuan.destination.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujuan.destination.R;
import com.liujuan.destination.dto.InterestResponse;
import com.liujuan.destination.ui.InstallGoogleMapDialog;
import com.liujuan.destination.utl.LayoutUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

    private List<InterestResponse> interests;
    private int photoWidthInPixel;
    private Context context;

    public InterestsAdapter(Context context) {
        this.context = context;
        photoWidthInPixel = (int) LayoutUtil.convertDpToPixel(context.getResources().getDimension(R.dimen.point_of_interest_photo_width), context);
    }

    public void setInterests(List<InterestResponse> interests) {
        this.interests = interests;
    }

    @Override
    public InterestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_city_details_interest_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestsAdapter.ViewHolder holder, int position) {
        final InterestResponse interest = interests.get(position);
        holder.interestName.setText(interest.getName());
        holder.interestAddress.setText(interest.getAddress());
        String text = context.getResources().getString(R.string.rating_raw) + interest.getRating();
        holder.interestRating.setText(text);
        if (interest.getPhotos() != null && !interest.getPhotos().isEmpty()) {
            String imageUrl = String.format(interest.getPhotos().get(0).getPhotoUrl(), photoWidthInPixel);
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.loading).error(R.drawable.item_error).fit().into(holder.interestImage);
        } else {
            holder.interestImage.setImageResource(R.drawable.item_error);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:" + interest.getLocation().getLat() + "," + interest.getLocation().getLng() + "?q=" + interest.getName() + "&z=14");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    InstallGoogleMapDialog.showDialog(((Activity) context).getFragmentManager());
                }
            }
        });
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
