package com.example.weatherapp.classes;

import android.util.Log;

import java.util.List;

public class ForecastWeather {
    public int message;
    public int cnt;
    public List<ForecastList> list;
    public ForecastCity city;

    public String toString(int indent) {
        String indentString = "";
        for (int i = 0; i < indent; i++) {
            indentString += "\t";
        }

        String returnval = String.format("%sForecast for: %s\n", indentString, city.getName());
        int numDisplay = 5;
        int i = 0;
        for (ForecastList item : list) {
            if(i < numDisplay) {
                returnval += item.toString(indent + 1);
                i++;
            }
        }
        Log.i("STR", "Return Value: " + returnval);
        return returnval;
    }
}
