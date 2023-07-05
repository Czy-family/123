package com.example.a123;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SavedCities {
    public static void saveCity(Context context, String cityName, String cityId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CityList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cityName, cityId);
        editor.apply();
    }

    public static Map<String, String> getCities(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CityList", Context.MODE_PRIVATE);
        return (Map<String, String>) sharedPreferences.getAll();
    }
}
