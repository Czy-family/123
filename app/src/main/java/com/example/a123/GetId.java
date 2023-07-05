package com.example.a123;

import android.content.Context;
import android.content.SharedPreferences;

public class GetId {
    public static String getCityId(Context context, String cityName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CityList", Context.MODE_PRIVATE);
        return sharedPreferences.getString(cityName, null);
    }
}
