package com.example.user.shake;

import java.util.Date;

public class RecyclerItem{

    private String name, owner, bike, content, date;
    private float rating;

    public RecyclerItem (){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String  getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public String getBike() {
        return bike;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
