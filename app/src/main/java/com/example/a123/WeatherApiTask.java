package com.example.a123;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
public class WeatherApiTask{
    private Context mContext;
    private static String TAG="ACTIVITY";
    private WeatherApiListener mListener;


    private String city;
    private String date;
    private String time;
    private String wendu;
    private String shidu;
    private double pm25;
    private String parent;
    private String citykey;
    private String result;

    private static String url="";
    public WeatherApiTask(Context context) {
        mContext = context;
       // mListener = listener;
    }
    public String getResult(){
        return this.result;
    }

    public String doInBackground(String... urls) {
        String result = "";
        try {
            // 创建 URL 对象
            URL url = new URL(urls[0]);

            // 创建 HttpURLConnection 对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 设置连接超时和读取超时时间
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 发起请求
            connection.connect();

            // 获取请求结果
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.result=result;
        return result;

    }

    public void onPostExecute(String result) {
        // 在这里处理请求结果
        Log.d(TAG, "API 请求结果：" + result);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            if(!message.isEmpty()){
                if(message.equals("success感谢又拍云(upyun.com)提供CDN赞助")){
                    Toast.makeText(mContext, "请求成功", Toast.LENGTH_SHORT).show();
                    int status = jsonObject.getInt("status");
                    String date = jsonObject.getString("date");
                    String time = jsonObject.getString("time");

                    // 解析cityInfo对象
                    JSONObject cityInfo = jsonObject.getJSONObject("cityInfo");
                    String city = cityInfo.getString("city");
                    String citykey = cityInfo.getString("citykey");
                    String parent = cityInfo.getString("parent");
                    String updateTime = cityInfo.getString("updateTime");

                    // 解析data对象
                    JSONObject data = jsonObject.getJSONObject("data");
                    String shidu = data.getString("shidu");
                    double pm25 = data.getDouble("pm25");
                    double pm10 = data.getDouble("pm10");
                    String quality = data.getString("quality");
                    String wendu = data.getString("wendu");
                    String ganmao = data.getString("ganmao");

                    // 解析forecast数组
                    JSONArray forecastArray = data.getJSONArray("forecast");
                    JSONObject forecast = forecastArray.getJSONObject(0);
                    String forecastDate = forecast.getString("date");
                    String high = forecast.getString("high");
                    String low = forecast.getString("low");
                    String ymd = forecast.getString("ymd");
                    String week = forecast.getString("week");
                    String sunrise = forecast.getString("sunrise");
                    String sunset = forecast.getString("sunset");
                    int aqi = forecast.getInt("aqi");
                    String fx = forecast.getString("fx");
                    String fl = forecast.getString("fl");
                    String type = forecast.getString("type");
                    String notice = forecast.getString("notice");

                    System.out.println("Forecast " + (0 + 1));
                    System.out.println("Date: " + forecastDate);
                    System.out.println("High: " + high);
                    System.out.println("Low: " + low);
                    System.out.println("YMD: " + ymd);
                    System.out.println("Week: " + week);
                    System.out.println("Sunrise: " + sunrise);
                    System.out.println("Sunset: " + sunset);
                    System.out.println("AQI: " + aqi);
                    System.out.println("FX: " + fx);
                    System.out.println("FL: " + fl);
                    System.out.println("Type: " + type);
                    System.out.println("Notice: " + notice);

                    // 解析yesterday对象
                    JSONObject yesterday = data.getJSONObject("yesterday");
                    String yesterdayDate = yesterday.getString("date");
                    String yesterdayHigh = yesterday.getString("high");
                    String yesterdayLow = yesterday.getString("low");
                    String yesterdayYmd = yesterday.getString("ymd");
                    String yesterdayWeek = yesterday.getString("week");
                    String yesterdaySunrise = yesterday.getString("sunrise");
                    String yesterdaySunset = yesterday.getString("sunset");
                    int yesterdayAqi = yesterday.getInt("aqi");
                    String yesterdayFx = yesterday.getString("fx");
                    String yesterdayFl = yesterday.getString("fl");
                    String yesterdayType = yesterday.getString("type");
                    String yesterdayNotice = yesterday.getString("notice");

                    System.out.println("Yesterday");
                    System.out.println("Date: " + yesterdayDate);
                    System.out.println("High: " + yesterdayHigh);
                    System.out.println("Low: " + yesterdayLow);
                    System.out.println("YMD: " + yesterdayYmd);
                    System.out.println("Week: " + yesterdayWeek);
                    System.out.println("Sunrise: " + yesterdaySunrise);
                    System.out.println("Sunset: " + yesterdaySunset);
                    System.out.println("AQI: " + yesterdayAqi);
                    System.out.println("FX: " + yesterdayFx);
                    System.out.println("FL: " + yesterdayFl);
                    System.out.println("Type: " + yesterdayType);
                    System.out.println("Notice: " + yesterdayNotice);

                    this.pm25 = pm25;
                    this.citykey = citykey;
                    this.shidu = shidu;
                    this.wendu = wendu;
                    this.parent = parent;
                    this.city = city;
                    this.date = date;
                    this.time = time;
                    System.out.println(this.shidu+"shidu");

                }else{
                    Toast.makeText(mContext, "error:"+message, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, message);
                }

            }else{
                Toast.makeText(mContext, "请求失败，请重试", Toast.LENGTH_SHORT).show();
                Log.d(TAG, message);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }



    public interface WeatherApiListener {
        void onApiSuccess(String weatherData);

        void onApiError();
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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
    public String getWendu() {
        return wendu;
    }
    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
    public String getShidu() {
        System.out.println(this.shidu+"shidu");
        return shidu;
    }
    public void setShidu(String shidu) {
        this.shidu = shidu;
    }
    public double getPm25() {
        return pm25;
    }
    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getCitykey() {
        return citykey;
    }
    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }
}
