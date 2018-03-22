package com.example.anhyunjin.brunch;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    private String title;
    private String date;
    private String content;
    private boolean image;
    private String  url;

    public Item() {

    }

    public Item(String title, String content, String date, boolean image, String url) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
        this.url = url;
    }

    public Item(String title, String content, String date){
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() { return this.title; }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public boolean getImage() { return this.image; }

    public String geturl() { return this.url; }


}
