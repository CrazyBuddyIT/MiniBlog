package com.tony.miniblog.listener;

import android.content.Context;
import android.support.v4.view.ViewPager;

/**
 * Created by Tony on 2015/2/18 0018.
 */
public class ViewPagerScrollListener implements ViewPager.OnPageChangeListener {

    private Context mainContext;
    private ViewPager vp;
    public ViewPagerScrollListener(Context ctx, ViewPager vp){
        this.mainContext = ctx;
        this.vp = vp ;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        vp.setCurrentItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
