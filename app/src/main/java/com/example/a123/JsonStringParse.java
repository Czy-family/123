package com.example.a123;
import com.google.gson.Gson;
public class JsonStringParse {
    private String jsonString = "";
    Gson gson;
    Weather weather;
    public JsonStringParse(){
        jsonString = "{\n" +
                "  \"time\": \"2018-09-22 12:37:21\",\n" +
                "  \"cityInfo\": {\n" +
                "    \"city\": \"天津市\",\n" +
                "    \"cityKey\": \"101030100\",\n" +
                "    \"parent\": \"天津\",\n" +
                "    \"updateTime\": \"12:32\"\n" +
                "  },\n" +
                "  \"date\": \"20180922\",\n" +
                "  \"message\": \"Success!\",\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "    \"shidu\": \"22%\",\n" +
                "    \"pm25\": 15.0,\n" +
                "    \"pm10\": 46.0,\n" +
                "    \"quality\": \"优\",\n" +
                "    \"wendu\": \"24\",\n" +
                "    \"ganmao\": \"各动\",\n" +
                "    \"forecast\": [\n" +
                "      {\n" +
                "        \"date\": \"22\",\n" +
                "        \"ymd\": \"2018-09-22\",\n" +
                "        \"week\": \"星\",\n" +
                "        \"sunrise\": \"05:57\",\n" +
                "        \"high\": \"26.0℃\",\n" +
                "        \"low\": \"15.0℃\",\n" +
                "        \"sunset\": \"18:10\",\n" +
                "        \"aqi\": 55.0,\n" +
                "        \"fx\": \"西\",\n" +
                "        \"fl\": \"4-5级\",\n" +
                "        \"type\": \"\",\n" +
                "        \"notice\": \"情\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"22\",\n" +
                "        \"ymd\": \"2018-09-22\",\n" +
                "        \"week\": \"星\",\n" +
                "        \"sunrise\": \"05:57\",\n" +
                "        \"high\": \"26.0℃\",\n" +
                "        \"low\": \"15.0℃\",\n" +
                "        \"sunset\": \"18:10\",\n" +
                "        \"aqi\": 55.0,\n" +
                "        \"fx\": \"西\",\n" +
                "        \"fl\": \"4-5级\",\n" +
                "        \"type\": \"\",\n" +
                "        \"notice\": \"情\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n";
    }
    public JsonStringParse(String jsonString){
        this.jsonString = jsonString;
        gson = new Gson();
        weather = gson.fromJson(jsonString, Weather.class);

    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
