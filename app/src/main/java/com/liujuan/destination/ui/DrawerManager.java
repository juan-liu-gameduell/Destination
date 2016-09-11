package com.liujuan.destination.ui;

import android.view.View;

import com.liujuan.destination.R;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class DrawerManager {
    private final List<DrawerItem> drawerItems = new ArrayList<>(2);
    private DrawerItem currentItem;
    private MainActivity mActivity;

    public DrawerManager(MainActivity activity, View parent) {
        mActivity = activity;
        final DrawerItem home = new DrawerItem(0, activity.getString(R.string.app_name), parent.findViewById(R.id.drawer_home));
        drawerItems.add(home);

        final DrawerItem favorite = new DrawerItem(1, activity.getString(R.string.favorite), parent.findViewById(R.id.drawer_favorite));
        drawerItems.add(favorite);
        home.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = home;
                updateSelection();
                mActivity.selectItem(currentItem.index);
            }
        });
        favorite.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = favorite;
                updateSelection();
                mActivity.selectItem(currentItem.index);
            }
        });
        currentItem = home;
        updateSelection();
    }

    private void updateSelection() {
        for (DrawerItem item : drawerItems) {
            item.view.setBackgroundColor(mActivity.getResources().getColor(android.R.color.transparent));
        }
        currentItem.view.setBackgroundColor(mActivity.getResources().getColor(R.color.drawer_item_selected));
    }

    public void setCurrentItemIndex(int index) {
        if (index >= drawerItems.size() || index < 0) {
            throw new InvalidParameterException("unsupported index:" + index);
        }
        currentItem = drawerItems.get(index);
        updateSelection();
    }

    public int getCurrentIndex() {
        return currentItem.index;
    }

    public String getCurrentTitle() {
        return currentItem.title;
    }

    private static class DrawerItem {
        private int index;
        private String title;
        private View view;

        private DrawerItem(int index, String title, View view) {
            this.index = index;
            this.title = title;
            this.view = view;
        }
    }
}
