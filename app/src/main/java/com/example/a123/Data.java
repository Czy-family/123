package com.example.a123;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("shidu")
    private String shidu;
    @SerializedName("pm25")
    private double pm25;
    @SerializedName("pm10")
    private double pm10;
    @SerializedName("quality")
    private String quality;
    @SerializedName("wendu")
    private String wendu;
    @SerializedName("ganmao")
    private String ganmao;
    @SerializedName("forecast")
    private Forecast forecast;
    public Data(){
        shidu = "253";
        wendu = "25";
        pm25=25.0;
        pm10=10.0;
        ganmao="8888";
        forecast = new Forecast();
    }
    public String getShidu() {
        return shidu;
    }

    public double getPm25() {
        return pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public String getQuality() {
        return quality;
    }

    public String getWendu() {
        return wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}

