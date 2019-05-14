package com.example.user.shake;

import java.util.Date;

public class RecyclerItem{

    private String name, owner, bike;
    private float rating;
    private int rentTime;
    private Date date;

    public RecyclerItem (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
