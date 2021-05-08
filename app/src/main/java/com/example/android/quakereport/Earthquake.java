package com.example.android.quakereport;

public class Earthquake {
    private Double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;
    public Earthquake(Double magnitude,String location,long timeInMilliseconds, String  url){
        mMagnitude=magnitude;
        mLocation=location;
        mTimeInMilliseconds=timeInMilliseconds;
        mUrl= url;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmLocation() {
        return mLocation;
    }

    public Double getmMagnitude() {
        return mMagnitude;
    }

    public String getmUrl() {
        return mUrl;
    }
}
