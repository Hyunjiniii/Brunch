package com.example.anhyunjin.brunch;

public class Item {
    private String title;
    private String sub_title;
    private String date;
    private String content;

    public Item() { }

    public Item(String title, String sub_title, String content, String date) {
        this.title = title;
        this.sub_title = sub_title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public String getSub_title() {
        return this.sub_title;
    }
}
