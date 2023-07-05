package com.example.a123;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("date")
    private String date;
    @SerializedName("ymd")
    private String ymd;
    @SerializedName("week")
    private String week;
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("high")
    private String high;
    @SerializedName("low")
    private String low;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("aqi")
    private double aqi;
    @SerializedName("fx")
    private String fx;
    @SerializedName("fl")
    private String fl;
    @SerializedName("type")
    private String type;
    @SerializedName("notice")
    private String notice;

    public Forecast(){
        high="25";
        low="10";

    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getYmd() {
        return ymd;
    }
    public void setYmd(String ymd) {
        this.ymd = ymd;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public String getSunrise() {
        return sunrise;
    }
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    public String getHigh() {
        return high;
    }
    public void setHigh(String high) {
        this.high = high;
    }
    public String getLow() {
        return low;
    }
    public void setLow(String low) {
        this.low = low;
    }
    public String getSunset() {
        return sunset;
    }
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    public double getAqi() {
        return aqi;
    }
    public void setAqi(double aqi) {
        this.aqi = aqi;
    }
    public String getFx() {
        return fx;
    }
    public void setFx(String fx) {
        this.fx = fx;
    }
    public String getFl() {
        return fl;
    }
    public void setFl(String fl) {
        this.fl = fl;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNotice() {
        return notice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }


}
