package com.example.user.shake;

public class ListVI {
    private String img;
    private String Title;
    private String context;
    private String Finish;

    public ListVI(String img, String title, String context,String finish) {
        this.img = img;
        Title = title;
        this.context = context;
        Finish=finish;
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

    public String getFinish() {
        return Finish;
    }

    public void setFinish(String finish) {
        Finish = finish;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}