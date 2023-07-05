package com.example.a123;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("cityinfo")
    private CityInfo cityInfo;
    @SerializedName("data")
    private Data data;
    @SerializedName("date")
    private String date="";
    @SerializedName("message")
    private String message="";
    @SerializedName("status")
    private int status=0;
    @SerializedName("time")
    private String time="";
    @SerializedName("forecast")
    private Forecast forecast;
    public Weather(){
        data = new Data();
        cityInfo = new CityInfo();
        date = "";
        message = "";
        status = 0;
        time = "";
        forecast = new Forecast();
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }
    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Forecast getForecast() {
        return forecast;
    }
    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
