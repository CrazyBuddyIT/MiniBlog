package com.tony.miniblog.listener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import com.tony.miniblog.utils.BlogConstants;

/**
 * Created by Tony on 2015/2/19 0019.
 */
public class MainPageTitleOnClickListener implements View.OnClickListener {

    private int title_index = 0;

    private Context ctx;
    private ViewPager vp;

    public MainPageTitleOnClickListener(int index) {
        this.title_index = index;
    }

    @Override
    public void onClick(View v) {
        Log.d("Main_Title_Click", "you have clicked the " + title_index + " tab!");
        switch (title_index) {
            case BlogConstants.MainBlogTitles.hotBlog:
                vp.setCurrentItem(0);
                break;
            case BlogConstants.MainBlogTitles.friendsBlogs:
                vp.setCurrentItem(1);
                break;
            case BlogConstants.MainBlogTitles.relatedMe:
                vp.setCurrentItem(2);
                break;
            case BlogConstants.MainBlogTitles.personalCenter:
                vp.setCurrentItem(3);
                break;
        }
    }

}
