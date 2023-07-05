package com.example.a123;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class WeatherAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Data> weatherDataList;

    public WeatherAdapter(Context context, ArrayList<Data> forecastList) {
        this.context = context;
        this.weatherDataList = forecastList;
    }

    @Override
    public int getCount() {
        return weatherDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weather_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dateTextView = view.findViewById(R.id.date_text_view);
            viewHolder.updateTimeTextView = view.findViewById(R.id.update_time_text_view);
            viewHolder.provinceTextView = view.findViewById(R.id.province_text_view);
            viewHolder.cityTextView = view.findViewById(R.id.city_text_view);
            viewHolder.temperatureTextView = view.findViewById(R.id.temperature_text_view);
            viewHolder.humidityTextView = view.findViewById(R.id.humidity_text_view);
            viewHolder.pm25TextView = view.findViewById(R.id.pm25_text_view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }
    private class ViewHolder {
        TextView dateTextView;
        TextView updateTimeTextView;
        TextView provinceTextView;
        TextView cityTextView;
        TextView temperatureTextView;
        TextView humidityTextView;
        TextView pm25TextView;
    }
}
