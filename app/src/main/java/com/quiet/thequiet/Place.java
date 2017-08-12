package com.quiet.thequiet;

/**
 * Created by eka on 2017. 8. 13..
 */

public class Place {

    //장소 정보
    String placeid;
    String placename;

    //업데이트일
    String lastupdate;

    //위도 경도
    double Latitude;
    double Logitude;

    //소음
    int decibel;

    public Place(String placeid, String placename, double latitude, double logitude, int decibel, String lastupdate) {
        this.placeid = placeid;
        this.placename = placename;
        this.lastupdate = lastupdate;
        Latitude = latitude;
        Logitude = logitude;
        this.decibel = decibel;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLogitude() {
        return Logitude;
    }

    public void setLogitude(double logitude) {
        Logitude = logitude;
    }

    public int getDecibel() {
        return decibel;
    }

    public void setDecibel(int decibel) {
        this.decibel = decibel;
    }
}
