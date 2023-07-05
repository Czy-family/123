package com.example.a123;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HuanCunActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listView;
    private List<String> cityList;
    private ArrayAdapter<String> arrayAdapter;

    SharedPreferences sharedPreferences0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_days);

        //获取
        listView = findViewById(R.id.weather_list_view1);
        cityList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(HuanCunActivity.this,android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(arrayAdapter);
        getHuanCunCities();
        arrayAdapter.notifyDataSetChanged();

        // 点击城市触发事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = cityList.get(position);
                getcity(cityName);
                turnToWeather(sharedPreferences0.getString("city",""),sharedPreferences0.getString("date",""),sharedPreferences0.getString("time",""),sharedPreferences0.getString("wendu",""),sharedPreferences0.getString("shidu",""),Double.valueOf(sharedPreferences0.getString("pm25","")),sharedPreferences0.getString("parent",""),sharedPreferences0.getString("citykey",""));
            }
        });
    }

    public void getHuanCunCities(){
        SharedPreferences[] sharedPreferences=new SharedPreferences[3];
        int now=0;
        //缓存的数据
        sharedPreferences[0] = getSharedPreferences("huancun1", Context.MODE_PRIVATE);
        sharedPreferences[1] = getSharedPreferences("huancun2", Context.MODE_PRIVATE);
        sharedPreferences[2] = getSharedPreferences("huancun3", Context.MODE_PRIVATE);

        String cityName1 = sharedPreferences[0].getString("city","");
        String cityName2 = sharedPreferences[1].getString("city","");
        String cityName3 = sharedPreferences[2].getString("city","");

        if(cityName1.isEmpty()&&cityName2.isEmpty()&&cityName3.isEmpty()){
            Toast.makeText(HuanCunActivity.this,"无最近访问的城市",Toast.LENGTH_SHORT).show();
        }else{
            if(!cityName1.isEmpty()){
                cityList.add(cityName1);
            }
            if(!cityName2.isEmpty()){
                cityList.add(cityName2);
            }
            if(!cityName3.isEmpty()){
                cityList.add(cityName3);
            }
        }
    }

    public void turnToWeather(String city,String date,String time,String wendu,String shidu,double pm25,String parent,String citykey){
        Intent intent = new Intent(HuanCunActivity.this,WeatherActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("wendu",wendu);
        intent.putExtra("shidu",shidu);
        intent.putExtra("pm25",pm25);
        intent.putExtra("parent",parent);
        intent.putExtra("citykey",citykey);
        startActivity(intent);
    }

    public void getcity(String name){
        SharedPreferences[] sharedPreferences=new SharedPreferences[3];
        int now=0;
        //缓存的数据
        sharedPreferences[0] = getSharedPreferences("huancun1", Context.MODE_PRIVATE);
        sharedPreferences[1] = getSharedPreferences("huancun2", Context.MODE_PRIVATE);
        sharedPreferences[2] = getSharedPreferences("huancun3", Context.MODE_PRIVATE);

        String cityName1 = sharedPreferences[0].getString("city","");
        String cityName2 = sharedPreferences[1].getString("city","");
        String cityName3 = sharedPreferences[2].getString("city","");

        if(name.equals(cityName1)){
            now=0;
        }else if(name.equals(cityName2)){
            now=1;
        }else{
            now=2;
        }
        sharedPreferences0 = sharedPreferences[now];
    }
}
