package com.tony.miniblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;    
import android.widget.TextView;
import com.tony.miniblog.R;
import com.tony.miniblog.model.BlogViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2015/3/6 0006.
 */
public class BlogsAdapter extends BaseAdapter {

    private Context ctx;
    private List<Map<String, Object>> hotBlogsDatas;
    private LayoutInflater hotBlogsInflater;

    public BlogsAdapter() {
    }

    public BlogsAdapter(Context ctx, List<Map<String, Object>> hotBlogsDatas) {
        this.ctx = ctx;
        this.hotBlogsDatas = hotBlogsDatas;
        this.hotBlogsInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.hotBlogsDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.hotBlogsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlogViewHolder blogViewHolder = null;

        if(null == convertView){
            blogViewHolder = new BlogViewHolder();
            convertView = hotBlogsInflater.inflate(R.layout.list_item_pg1, null);
            blogViewHolder.blogIcon = (ImageView)convertView.findViewById(R.id.iv_pg1_hotblogs);
            blogViewHolder.blogTitle = (TextView)convertView.findViewById(R.id.tv_pg1_title);
            blogViewHolder.blogContent = (TextView)convertView.findViewById(R.id.tv_pg1_content);
            convertView.setTag(blogViewHolder);
        }else {
            blogViewHolder = (BlogViewHolder)convertView.getTag();
        }
        blogViewHolder.blogIcon.setBackgroundResource(Integer.valueOf(hotBlogsDatas.get(position).get("blogIcon").toString()));
        blogViewHolder.blogTitle.setText(hotBlogsDatas.get(position).get("blogTitle").toString());
        blogViewHolder.blogContent.setText(hotBlogsDatas.get(position).get("blogContent").toString());

        return convertView;
    }
}
