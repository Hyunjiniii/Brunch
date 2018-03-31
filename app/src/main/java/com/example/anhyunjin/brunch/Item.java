package com.example.anhyunjin.brunch;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    private String title;
    private String date;
    private String content;
    private boolean image;
    private String url;
    private String align;

    public Item() {

    }

    public Item(String title, String content, String date, boolean image, String url, String align) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
        this.url = url;
        this.align = align;
    }

    public Item(String title, String content, String date, String align){
        this.title = title;
        this.content = content;
        this.date = date;
        this.align = align;
    }

    public String getAlign() {return this.align;}

    public String getTitle() { return this.title; }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public boolean isImage() { return this.image; }

    public String geturl() { return this.url; }


}
