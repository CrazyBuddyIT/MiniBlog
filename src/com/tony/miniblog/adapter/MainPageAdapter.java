package com.tony.miniblog.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Tony on 2015/2/18 0018.
 */
public class MainPageAdapter extends PagerAdapter {

    private List<View> viewsList;

    public MainPageAdapter(List<View> views){
        this.viewsList = views;
    }

    @Override
    public int getCount() {
        return viewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parentView = (ViewGroup) viewsList.get(position).getParent();
        if(null !=  parentView) {
            parentView.removeAllViews();
        }
        View v = viewsList.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewsList.get(position));
    }
}
