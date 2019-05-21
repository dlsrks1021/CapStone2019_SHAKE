package com.example.user.shake;

import java.io.Serializable;

public class BikeInfo implements Serializable {

    private String bikeOwner, bikeCode, bikeImgUrl, bikeLockId, bikeModelName, bikeType, bikeAddInfo;
    private double bikeLatitude, bikeLongitude;
    private int bikeCost;
    private float bikeRating;

    public BikeInfo(String bikeOwner, String bikeCode, double bikeLatitude
    , double bikeLongitude, int bikeCost, String bikeImgUrl, String bikeLockId
    , String bikeModelName, String bikeType, String bikeAddInfo){
        this.bikeOwner = bikeOwner;
        this.bikeCode = bikeCode;
        this.bikeLatitude = bikeLatitude;
        this.bikeLongitude = bikeLongitude;
        this.bikeCost = bikeCost;
        this.bikeImgUrl = bikeImgUrl;
        this.bikeLockId = bikeLockId;
        this.bikeModelName = bikeModelName;
        this.bikeType = bikeType;
        this.bikeAddInfo = bikeAddInfo;
    }

    public float getBikeRating() {
        return bikeRating;
    }

    public void setBikeRating(float bikeRating) {
        this.bikeRating = bikeRating;
    }

    public String getBikeOwner() {
        return bikeOwner;
    }
    public double getBikeLatitude(){
        return bikeLatitude;
    }
    public String getBikeCode(){
        return bikeCode;
    }

    public double getBikeLongitude() {
        return bikeLongitude;
    }

    public String getBikeType() {
        return bikeType;
    }
}
