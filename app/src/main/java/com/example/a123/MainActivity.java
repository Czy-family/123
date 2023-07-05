package com.example.a123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String str;
    //String jsonString = "{\"message\":\"success感谢又拍云(upyun.com)提供CDN赞助\",\"status\":200,\"date\":\"20230630\",\"time\":\"2023-06-30 20:50:35\",\"cityInfo\":{\"city\":\"天津市\",\"citykey\":\"101030100\",\"parent\":\"天津\",\"updateTime\":\"16:16\"},\"data\":{\"shidu\":\"25%\",\"pm25\":8.0,\"pm10\":15.0,\"quality\":\"优\",\"wendu\":\"37\",\"ganmao\":\"各类人群可自由活动\",\"forecast\":[{\"date\":\"30\",\"high\":\"高温 39℃\",\"low\":\"低温 24℃\",\"ymd\":\"2023-06-30\",\"week\":\"星期五\",\"sunrise\":\"04:48\",\"sunset\":\"19:41\",\"aqi\":105,\"fx\":\"西南风\",\"fl\":\"3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"01\",\"high\":\"高温 39℃\",\"low\":\"低温 27℃\",\"ymd\":\"2023-07-01\",\"week\":\"星期六\",\"sunrise\":\"04:49\",\"sunset\":\"19:41\",\"aqi\":110,\"fx\":\"南风\",\"fl\":\"3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"}],\"yesterday\":{\"date\":\"29\",\"high\":\"高温 37℃\",\"low\":\"低温 21℃\",\"ymd\":\"2023-06-29\",\"week\":\"星期四\",\"sunrise\":\"04:48\",\"sunset\":\"19:41\",\"aqi\":94,\"fx\":\"东风\",\"fl\":\"3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"}}}";

    private EditText cityIdEditText;
    private Button searchButton;
    private Button refreshButton;
    //
    private Button noticeBtn;
    private ListView weatherListView;
    private WeatherAdapter weatherAdapter;
    private ArrayList<Data> dataArrayList;
    //private JsonStringParse jsonStringParse;
    //MessageReturn messageReturn;
    private static final String PREFS_NAME = "WeatherCache";
    private static final long CACHE_EXPIRATION_TIME_MS = 2 * 60 * 60 * 1000; // 2 hours

    private SharedPreferences sharedPreferences;
    private String cityCode;


    //缓存和城市列表
    private SavedCities savedCities;
    private ListView listView;
    private List<String> cityList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取用户输入城市id点击刷新按钮的信息
        cityIdEditText = findViewById(R.id.city_id_edit_text);
        searchButton = findViewById(R.id.search_button);
        refreshButton = findViewById(R.id.refresh_button);

        //
        noticeBtn = findViewById(R.id.notice_button);


        weatherListView = findViewById(R.id.weather_list_view);
        dataArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(MainActivity.this, dataArrayList);
        weatherListView.setAdapter(weatherAdapter);

        //用户关注的获取城市列表
        listView = findViewById(R.id.weather_list_view);
        cityList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(arrayAdapter);

        // 读取关注的城市列表并显示
        Map<String, String> cities = SavedCities.getCities(this);
        for (String cityName : cities.keySet()) {
            cityList.add(cityName);
        }
        arrayAdapter.notifyDataSetChanged();

        //用户点击查找事件操作
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityId = cityIdEditText.getText().toString();
                cityCode=cityId;
                if (cityId.length() != 9) {
                    Toast.makeText(MainActivity.this, "请输入正确的城市ID", Toast.LENGTH_SHORT).show();
                } else {
                    // 启动异步任务进行 API 请求
                    new ApiRequestTask().execute("http://t.weather.itboy.net/api/weather/city/"+cityCode);
                    //System.out.println(str);
                    //Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                    //messageReturn = new MessageReturn();
                    // 创建JSONObject对象
                    // 解析顶层属性

                }
            }
        });

        //用户点击刷新按钮事件
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> cities = SavedCities.getCities(MainActivity.this);
                cityList.clear();
                for (String cityName : cities.keySet()) {
                    cityList.add(cityName);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });



        // 点击城市触发事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = cityList.get(position);
                String cityId = GetId.getCityId(MainActivity.this, cityName);

                // 根据城市id获取天气数据并显示
                // 进行一系列操作...
                if (cityId.length() != 9) {
                    Toast.makeText(MainActivity.this, "请输入正确的城市ID", Toast.LENGTH_SHORT).show();
                } else {
                    // 启动异步任务进行 API 请求
                    new ApiRequestTask().execute("http://t.weather.itboy.net/api/weather/city/"+cityId);
                }
            }
        });

        //点击近三天访问过的数据
        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HuanCunActivity.class);
                startActivity(intent);
            }
        });
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
            Log.d(TAG, "API 请求结果：" + result);
            strjson=result;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                if(!message.isEmpty()){
                    if(message.equals("success感谢又拍云(upyun.com)提供CDN赞助")){
                        Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                            intent.putExtra("city",city);
                            intent.putExtra("date",date);
                            intent.putExtra("time",time);
                            intent.putExtra("wendu",wendu);
                            intent.putExtra("shidu",shidu);
                            intent.putExtra("pm25",pm25);
                            intent.putExtra("parent",parent);
                            intent.putExtra("citykey",citykey);
                            startActivity(intent);
                            threeDays(city,date,time,wendu,shidu,pm25,parent,citykey);


                        }else {
                            Toast.makeText(MainActivity.this, "分析数据失败" + message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, message);
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "error:"+message, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, message);
                    }

                }else{
                    Toast.makeText(MainActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, message);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void threeDays(String city,String date,String time,String wendu,String shidu,double pm25,String parent,String citykey){
        SharedPreferences[] sharedPreferences=new SharedPreferences[3];
        int now=0;
        //缓存的数据
        sharedPreferences[0] = getSharedPreferences("huancun1", Context.MODE_PRIVATE);
        sharedPreferences[1] = getSharedPreferences("huancun2", Context.MODE_PRIVATE);
        sharedPreferences[2] = getSharedPreferences("huancun3", Context.MODE_PRIVATE);

        String cityName1 = sharedPreferences[0].getString("city",null);
        String cityName2 = sharedPreferences[1].getString("city",null);
        String cityName3 = sharedPreferences[2].getString("city",null);
        if(TextUtils.isEmpty(cityName1)){
            // 获取SharedPreferences的编辑器
            SharedPreferences.Editor editor = sharedPreferences[0].edit();
            // 将数据字符串保存到SharedPreferences中
            editor.putString("city", city);
            editor.putString("date",date);
            editor.putString("time",time);
            editor.putString("wendu",wendu);
            editor.putString("shidu",shidu);
            editor.putString("pm25",String.valueOf(pm25));
            editor.putString("parent",parent);
            editor.putString("citykey",citykey);
            editor.apply();
            now+=1;
            //添加的数据
        }else{
            if(TextUtils.isEmpty(cityName2)){
                if(!city.equals(cityName1)){
                    // 获取SharedPreferences的编辑器
                    SharedPreferences.Editor editor = sharedPreferences[1].edit();
                    // 将数据字符串保存到SharedPreferences中
                    editor.putString("city", city);
                    editor.putString("date",date);
                    editor.putString("time",time);
                    editor.putString("wendu",wendu);
                    editor.putString("shidu",shidu);
                    editor.putString("pm25",String.valueOf(pm25));
                    editor.putString("parent",parent);
                    editor.putString("citykey",citykey);
                    editor.apply();
                    now+=1;
                    //添加的数据
                }

            }else{
                if(TextUtils.isEmpty(cityName3)){
                    if(!city.equals(cityName1)&&!city.equals(cityName2)){
                        // 获取SharedPreferences的编辑器
                        SharedPreferences.Editor editor = sharedPreferences[2].edit();
                        // 将数据字符串保存到SharedPreferences中
                        editor.putString("city", city);
                        editor.putString("date",date);
                        editor.putString("time",time);
                        editor.putString("wendu",wendu);
                        editor.putString("shidu",shidu);
                        editor.putString("pm25",String.valueOf(pm25));
                        editor.putString("parent",parent);
                        editor.putString("citykey",citykey);
                        editor.apply();
                        now+=1;
                        //添加的数据
                    }

                }else{

                    SharedPreferences.Editor editor = sharedPreferences[0].edit();
                    editor.clear().apply();
                    editor.putString("city",sharedPreferences[1].getString("city",""));
                    editor.putString("date",sharedPreferences[1].getString("date",""));
                    editor.putString("time",sharedPreferences[1].getString("time",""));
                    editor.putString("wendu",sharedPreferences[1].getString("wendu",""));
                    editor.putString("shidu",sharedPreferences[1].getString("shidu",""));
                    editor.putString("parent",sharedPreferences[1].getString("parent",""));
                    editor.putString("citykey",sharedPreferences[1].getString("citykey",""));
                    editor.putString("pm25",sharedPreferences[1].getString("pm25",""));
                    editor.apply();
                    /*
                    Map<String,String> map= (Map<String, String>) sharedPreferences[1].getAll();
                    for (Map.Entry<String, String> entry : map.entrySet()){
                        String key = entry.getKey();
                        String value = entry.getValue();
                        editor.putString(key,value);
                    }
                    */

                    SharedPreferences.Editor editor1 = sharedPreferences[1].edit();
                    editor1.clear().apply();
                    editor1.putString("city",sharedPreferences[2].getString("city",""));
                    editor1.putString("date",sharedPreferences[2].getString("date",""));
                    editor1.putString("time",sharedPreferences[2].getString("time",""));
                    editor1.putString("wendu",sharedPreferences[2].getString("wendu",""));
                    editor1.putString("shidu",sharedPreferences[2].getString("shidu",""));
                    editor1.putString("parent",sharedPreferences[2].getString("parent",""));
                    editor1.putString("citykey",sharedPreferences[2].getString("citykey",""));
                    editor1.putString("pm25",sharedPreferences[2].getString("pm25",""));
                    editor1.apply();
                    /*
                    Map<String,String> map1= (Map<String, String>) sharedPreferences[2].getAll();
                    for (Map.Entry<String, String> entry : map1.entrySet()){
                        String key = entry.getKey();
                        String value = entry.getValue();
                        editor1.putString(key,value);
                    }
                     */
                    SharedPreferences.Editor editor2 = sharedPreferences[2].edit();
                    editor2.clear().apply();
                    editor2.putString("city", city);
                    editor2.putString("date",date);
                    editor2.putString("time",time);
                    editor2.putString("wendu",wendu);
                    editor2.putString("shidu",shidu);
                    editor2.putString("pm25",String.valueOf(pm25));
                    editor2.putString("parent",parent);
                    editor2.putString("citykey",citykey);
                    editor2.apply();
                }
            }
        }

    }

}