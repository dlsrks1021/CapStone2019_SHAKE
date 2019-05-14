package com.example.user.shake;

public class ListVO {
    private String img;
    private String Title;
    private String context;

    public ListVO(String img, String title, String context) {
        this.img = img;
        Title = title;
        this.context = context;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}