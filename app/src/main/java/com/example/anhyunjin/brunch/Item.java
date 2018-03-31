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
    private String font;

    public Item() {

    }

    public Item(String title, String content, String date, boolean image, String url, String align, String font) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
        this.url = url;
        this.align = align;
        this.font = font;
    }

    public Item(String title, String content, String date, String align, String font){
        this.title = title;
        this.content = content;
        this.date = date;
        this.align = align;
        this.font = font;
    }

    public String getFont() {return this.font;}

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
