package com.example.a123;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    private Button btnFollow;
    TextView textView ;
    TextView datetext;
    TextView timetext;
    TextView wendutext;
    TextView shidutext;
    TextView pm25text;

    TextView parenttext;

    private Button btnrefresh;
    WeatherApiTask weatherApiTask = new WeatherApiTask(WeatherActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_item);

        textView = findViewById(R.id.city_text_view);
        datetext = findViewById(R.id.date_text_view);
        timetext = findViewById(R.id.update_time_text_view);
        wendutext = findViewById(R.id.temperature_text_view);
        shidutext = findViewById(R.id.humidity_text_view);
        pm25text = findViewById(R.id.pm25_text_view);
        parenttext = findViewById(R.id.pm25_text_view);

        btnrefresh = findViewById(R.id.btn_refresh);
        // 获取传递的新值
        String newValue = getIntent().getStringExtra("city");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String wendu = getIntent().getStringExtra("wendu");
        String shidu =  getIntent().getStringExtra("shidu");
        String pm25=getIntent().getStringExtra("pm25");
        String parent = getIntent().getStringExtra("parent");
        String citykey = getIntent().getStringExtra("citykey");
        // 找到要设置新值的控件，假设为TextView，示例代码如下：

        // 设置新的值
        textView.setText(newValue);

        datetext.setText("日期:"+date);

        timetext.setText("系统更新时间:"+time);

        wendutext.setText("温度："+wendu+"℃");

        shidutext.setText("湿度："+shidu);

        pm25text.setText("pm2.5"+pm25);

        parenttext.setText("省："+parent);

        //点击关注按钮后保存城市信息
        btnFollow = findViewById(R.id.btn_follow);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前城市名称和id
                // 要保存的文件名"CityList"，cityList是要保存的数据列表
                // 获取SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("CityList", Context.MODE_PRIVATE);

                // 获取SharedPreferences的编辑器
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 将数据字符串保存到SharedPreferences中
                editor.putString(newValue, citykey);
                editor.apply();

                // 文件保存成功
                Toast.makeText(WeatherActivity.this, "文件保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        //点击刷新事件
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    refresh(citykey);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    public void refresh(String cityId) throws InterruptedException {
        new ApiRequestTask().execute("http://t.weather.itboy.net/api/weather/city/"+cityId);
        //Toast.makeText(WeatherActivity.this, "数据刷新成功", Toast.LENGTH_SHORT).show();
    }

    private class ApiRequestTask extends AsyncTask<String, Void, String> {
        private String strjson = "";
        @Override
        protected String doInBackground(String... urls) {
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
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // 在这里处理请求结果
            Log.d("WeatherActivity", "API 请求结果：" + result);
            strjson=result;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                if(!message.isEmpty()){
                    if(message.equals("success感谢又拍云(upyun.com)提供CDN赞助")){
                        Toast.makeText(WeatherActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
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

                        if(message.equals("success感谢又拍云(upyun.com)提供CDN赞助")){

                            // 设置新的值
                            textView.setText(city);

                            datetext.setText("日期:"+date);

                            timetext.setText("系统更新时间:"+time);

                            wendutext.setText("温度："+wendu+"℃");

                            shidutext.setText("湿度："+shidu);

                            pm25text.setText("pm2.5"+pm25);

                            parenttext.setText("省："+parent);
                            Toast.makeText(WeatherActivity.this, "数据刷新成功", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(WeatherActivity.this, "分析数据失败" + message, Toast.LENGTH_SHORT).show();
                            Log.d("WeatherActivity", message);
                        }
                    }else{
                        Toast.makeText(WeatherActivity.this, "error:"+message, Toast.LENGTH_SHORT).show();
                        Log.d("WeatherActivity", message);
                    }

                }else{
                    Toast.makeText(WeatherActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.d("WeatherActivity", message);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
