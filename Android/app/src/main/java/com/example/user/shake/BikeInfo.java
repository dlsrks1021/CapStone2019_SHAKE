package com.example.user.shake;

public class BikeInfo {

    private String bikeOwner, bikeCode, bikeImgUrl, bikeLockId, bikeModelName, bikeType;
    private float bikeLatitude, bikeLongitude;
    private int bikeCost;

    public BikeInfo(String bikeOwner, String bikeCode, float bikeLatitude
    , float bikeLongitude, int bikeCost, String bikeImgUrl, String bikeLockId
    , String bikeModelName, String bikeType){
        this.bikeOwner = bikeOwner;
        this.bikeCode = bikeCode;
        this.bikeLatitude = bikeLatitude;
        this.bikeLongitude = bikeLongitude;
        this.bikeCost = bikeCost;
        this.bikeImgUrl = bikeImgUrl;
        this.bikeLockId = bikeLockId;
        this.bikeModelName = bikeModelName;
        this.bikeType = bikeType;
    }
}
