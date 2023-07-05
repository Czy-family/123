package com.example.a123;

import com.google.gson.annotations.SerializedName;

public class CityInfo {
    @SerializedName("city")
    private String city="";
    @SerializedName("citykey")
    private  String citykey="101010100";
    @SerializedName("parent")
    private String parent="0";
    @SerializedName("updateTime")
    private String updateTime="00:00:00";
    public CityInfo(){
        city = "北京";
        citykey="101010100";
        parent="0";
        updateTime="00:00:00";
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitykey() {
        return citykey;
    }

    public String getParent() {

        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setCityKey(String cityKey) {
        this.citykey = citykey;
    }
}
