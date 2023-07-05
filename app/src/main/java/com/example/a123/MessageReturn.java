package com.example.a123;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MessageReturn {
    private String jsonString;
    private CityInfo cityInfo;
    private Data data;
    private String date="";
    private String message="";
    private int status=0;
    private String time="";
    private Forecast forecast;
    public MessageReturn(){
        this.jsonString = getJsonString();
    }
    public MessageReturn(String jsonstr0){
        this.jsonString=jsonstr0;
        cityInfo = new CityInfo();
        data = new Data();
        forecast = new Forecast();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            message = jsonObject.getString("message");
            status = jsonObject.getInt("status");
            if(!message.isEmpty()){
                if(message.equals("success感谢又拍云(upyun.com)提供CDN赞助")) {
                    time = jsonObject.getString("time");

                    JSONObject cityInfoObject = jsonObject.getJSONObject("cityInfo");

                    cityInfo.setCity(cityInfoObject.getString("city"));
                    cityInfo.setCityKey(cityInfoObject.getString("cityKey"));
                    cityInfo.setParent(cityInfoObject.getString("parent"));
                    cityInfo.setUpdateTime(cityInfoObject.getString("updateTime"));

                    date = jsonObject.getString("date");


                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    data.setShidu(dataObject.getString("shidu"));
                    data.setPm25(dataObject.getDouble("pm25"));
                    data.setPm10(dataObject.getDouble("pm10"));
                    data.setWendu(dataObject.getString("wendu"));
                    data.setGanmao(dataObject.getString("ganmao"));

                    JSONArray forecastArray = dataObject.getJSONArray("forecast");
                    JSONObject forecastObject = forecastArray.getJSONObject(0);

                    forecast.setHigh(forecastObject.getString("high"));
                    forecast.setLow(forecastObject.getString("low"));
                    forecast.setWeek(forecastObject.getString("week"));
                    forecast.setYmd(forecastObject.getString("ymd"));
                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getJsonString() {
        jsonString="{\n" +
                "\t\"message\": \"success感谢又拍云(upyun.com)提供CDN赞助\",\n" +
                "\t\"status\": 200,\n" +
                "\t\"date\": \"20230630\",\n" +
                "\t\"time\": \"2023-06-30 20:50:35\",\n" +
                "\t\"cityInfo\": {\n" +
                "\t\t\"city\": \"天津市\",\n" +
                "\t\t\"citykey\": \"101030100\",\n" +
                "\t\t\"parent\": \"天津\",\n" +
                "\t\t\"updateTime\": \"16:16\"\n" +
                "\t},\n" +
                "\t\"data\": {\n" +
                "\t\t\"shidu\": \"25%\",\n" +
                "\t\t\"pm25\": 8.0,\n" +
                "\t\t\"pm10\": 15.0,\n" +
                "\t\t\"quality\": \"优\",\n" +
                "\t\t\"wendu\": \"37\",\n" +
                "\t\t\"ganmao\": \"各类人群可自由活动\",\n" +
                "\t\t\"forecast\": [{\n" +
                "\t\t\t\"date\": \"30\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 24℃\",\n" +
                "\t\t\t\"ymd\": \"2023-06-30\",\n" +
                "\t\t\t\"week\": \"星期五\",\n" +
                "\t\t\t\"sunrise\": \"04:48\",\n" +
                "\t\t\t\"sunset\": \"19:41\",\n" +
                "\t\t\t\"aqi\": 105,\n" +
                "\t\t\t\"fx\": \"西南风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"晴\",\n" +
                "\t\t\t\"notice\": \"愿你拥有比阳光明媚的心情\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"01\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 27℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-01\",\n" +
                "\t\t\t\"week\": \"星期六\",\n" +
                "\t\t\t\"sunrise\": \"04:49\",\n" +
                "\t\t\t\"sunset\": \"19:41\",\n" +
                "\t\t\t\"aqi\": 110,\n" +
                "\t\t\t\"fx\": \"南风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"02\",\n" +
                "\t\t\t\"high\": \"高温 34℃\",\n" +
                "\t\t\t\"low\": \"低温 25℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-02\",\n" +
                "\t\t\t\"week\": \"星期日\",\n" +
                "\t\t\t\"sunrise\": \"04:49\",\n" +
                "\t\t\t\"sunset\": \"19:41\",\n" +
                "\t\t\t\"aqi\": 100,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"03\",\n" +
                "\t\t\t\"high\": \"高温 32℃\",\n" +
                "\t\t\t\"low\": \"低温 24℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-03\",\n" +
                "\t\t\t\"week\": \"星期一\",\n" +
                "\t\t\t\"sunrise\": \"04:50\",\n" +
                "\t\t\t\"sunset\": \"19:41\",\n" +
                "\t\t\t\"aqi\": 97,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"阴\",\n" +
                "\t\t\t\"notice\": \"不要被阴云遮挡住好心情\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"04\",\n" +
                "\t\t\t\"high\": \"高温 31℃\",\n" +
                "\t\t\t\"low\": \"低温 24℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-04\",\n" +
                "\t\t\t\"week\": \"星期二\",\n" +
                "\t\t\t\"sunrise\": \"04:50\",\n" +
                "\t\t\t\"sunset\": \"19:40\",\n" +
                "\t\t\t\"aqi\": 85,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"05\",\n" +
                "\t\t\t\"high\": \"高温 36℃\",\n" +
                "\t\t\t\"low\": \"低温 26℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-05\",\n" +
                "\t\t\t\"week\": \"星期三\",\n" +
                "\t\t\t\"sunrise\": \"04:51\",\n" +
                "\t\t\t\"sunset\": \"19:40\",\n" +
                "\t\t\t\"aqi\": 80,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"晴\",\n" +
                "\t\t\t\"notice\": \"愿你拥有比阳光明媚的心情\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"06\",\n" +
                "\t\t\t\"high\": \"高温 38℃\",\n" +
                "\t\t\t\"low\": \"低温 27℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-06\",\n" +
                "\t\t\t\"week\": \"星期四\",\n" +
                "\t\t\t\"sunrise\": \"04:51\",\n" +
                "\t\t\t\"sunset\": \"19:40\",\n" +
                "\t\t\t\"aqi\": 96,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"07\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 26℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-07\",\n" +
                "\t\t\t\"week\": \"星期五\",\n" +
                "\t\t\t\"sunrise\": \"04:52\",\n" +
                "\t\t\t\"sunset\": \"19:40\",\n" +
                "\t\t\t\"aqi\": 92,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"2级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"08\",\n" +
                "\t\t\t\"high\": \"高温 32℃\",\n" +
                "\t\t\t\"low\": \"低温 24℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-08\",\n" +
                "\t\t\t\"week\": \"星期六\",\n" +
                "\t\t\t\"sunrise\": \"04:53\",\n" +
                "\t\t\t\"sunset\": \"19:39\",\n" +
                "\t\t\t\"aqi\": 79,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"2级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"09\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 24℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-09\",\n" +
                "\t\t\t\"week\": \"星期日\",\n" +
                "\t\t\t\"sunrise\": \"04:53\",\n" +
                "\t\t\t\"sunset\": \"19:39\",\n" +
                "\t\t\t\"aqi\": 70,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"2级\",\n" +
                "\t\t\t\"type\": \"阴\",\n" +
                "\t\t\t\"notice\": \"不要被阴云遮挡住好心情\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"10\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 25℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-10\",\n" +
                "\t\t\t\"week\": \"星期一\",\n" +
                "\t\t\t\"sunrise\": \"04:54\",\n" +
                "\t\t\t\"sunset\": \"19:39\",\n" +
                "\t\t\t\"aqi\": 48,\n" +
                "\t\t\t\"fx\": \"东南风\",\n" +
                "\t\t\t\"fl\": \"2级\",\n" +
                "\t\t\t\"type\": \"小雨\",\n" +
                "\t\t\t\"notice\": \"雨虽小，注意保暖别感冒\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"11\",\n" +
                "\t\t\t\"high\": \"高温 35℃\",\n" +
                "\t\t\t\"low\": \"低温 25℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-11\",\n" +
                "\t\t\t\"week\": \"星期二\",\n" +
                "\t\t\t\"sunrise\": \"04:55\",\n" +
                "\t\t\t\"sunset\": \"19:38\",\n" +
                "\t\t\t\"aqi\": 76,\n" +
                "\t\t\t\"fx\": \"南风\",\n" +
                "\t\t\t\"fl\": \"2级\",\n" +
                "\t\t\t\"type\": \"阴\",\n" +
                "\t\t\t\"notice\": \"不要被阴云遮挡住好心情\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"12\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 26℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-12\",\n" +
                "\t\t\t\"week\": \"星期三\",\n" +
                "\t\t\t\"sunrise\": \"04:55\",\n" +
                "\t\t\t\"sunset\": \"19:38\",\n" +
                "\t\t\t\"aqi\": 71,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"13\",\n" +
                "\t\t\t\"high\": \"高温 39℃\",\n" +
                "\t\t\t\"low\": \"低温 25℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-13\",\n" +
                "\t\t\t\"week\": \"星期四\",\n" +
                "\t\t\t\"sunrise\": \"04:56\",\n" +
                "\t\t\t\"sunset\": \"19:37\",\n" +
                "\t\t\t\"aqi\": 71,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"多云\",\n" +
                "\t\t\t\"notice\": \"阴晴之间，谨防紫外线侵扰\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"14\",\n" +
                "\t\t\t\"high\": \"高温 37℃\",\n" +
                "\t\t\t\"low\": \"低温 26℃\",\n" +
                "\t\t\t\"ymd\": \"2023-07-14\",\n" +
                "\t\t\t\"week\": \"星期五\",\n" +
                "\t\t\t\"sunrise\": \"04:57\",\n" +
                "\t\t\t\"sunset\": \"19:37\",\n" +
                "\t\t\t\"aqi\": 72,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"阴\",\n" +
                "\t\t\t\"notice\": \"不要被阴云遮挡住好心情\"\n" +
                "\t\t}],\n" +
                "\t\t\"yesterday\": {\n" +
                "\t\t\t\"date\": \"29\",\n" +
                "\t\t\t\"high\": \"高温 37℃\",\n" +
                "\t\t\t\"low\": \"低温 21℃\",\n" +
                "\t\t\t\"ymd\": \"2023-06-29\",\n" +
                "\t\t\t\"week\": \"星期四\",\n" +
                "\t\t\t\"sunrise\": \"04:48\",\n" +
                "\t\t\t\"sunset\": \"19:41\",\n" +
                "\t\t\t\"aqi\": 94,\n" +
                "\t\t\t\"fx\": \"东风\",\n" +
                "\t\t\t\"fl\": \"3级\",\n" +
                "\t\t\t\"type\": \"晴\",\n" +
                "\t\t\t\"notice\": \"愿你拥有比阳光明媚的心情\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        return jsonString;
    }
    public void setJsonString(String jsonObject) {
        this.jsonString = jsonObject;
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
