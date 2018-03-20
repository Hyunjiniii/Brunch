package com.example.anhyunjin.brunch;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    private String title;
    private String date;
    private String content;
    private String image;
    private boolean url;

    public Item() {

    }

    public Item(String title, String content, String date, String image, boolean url) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
        this.url = url;
    }

    public String getTitle() { return this.title; }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public String getImage() { return this.image; }

    public boolean isUrl() { return this.url; }


}
