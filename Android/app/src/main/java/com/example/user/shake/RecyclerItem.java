package com.example.user.shake;

import java.util.Date;

public class RecyclerItem{

    private String name, owner, bike, content;
    private float rating;
    private int rentTime;
    private Date date;

    public RecyclerItem (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public int getRentTime() {
        return rentTime;
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
}
