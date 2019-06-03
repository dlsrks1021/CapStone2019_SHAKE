package com.example.user.shake;

public class ListVT {
    private String img;
    private String Title;
    private String context;
    private String Finish;
    private String Renttime;
    private String RequestCode;

    public ListVT(String img, String title, String context,String finish,String renttime,String requestCode) {
        this.img = img;
        Title = title;
        this.context = context;
        Renttime=renttime;
        RequestCode=requestCode;
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

    public String getRenttime() {
        return Renttime;
    }

    public void setRenttime(String renttime) {
        Renttime = renttime;
    }

    public String getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(String requestCode) {
        RequestCode = requestCode;
    }
}