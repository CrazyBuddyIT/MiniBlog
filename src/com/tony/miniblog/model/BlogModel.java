package com.tony.miniblog.model;

/**
 * Created by Tony on 2015/3/6 0006.
 */
public class BlogModel {

    private long blogId;
    private String blogTitle;
    private String blogContent;
    private String blogDate;

    public BlogModel() {
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(String blogDate) {
        this.blogDate = blogDate;
    }

    public BlogModel(long blogId, String blogTitle, String blogContent, String blogDate) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.blogDate = blogDate;
    }
}
