package com.example.weatherapp.classes;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastList {
    @SerializedName("main")
    public ForecastConditions measurements;
    public List<ForecastItem> weather; // listed forecast items
    public ForecastWind wind;
    public int visibility;
    public double pop;
    @SerializedName("dt_txt")
    public String datetime; // TIME

    public String getTime(){
        return this.datetime;
    }

    public ForecastConditions getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ForecastConditions measurements) {
        this.measurements = measurements;
    }

    public List<ForecastItem> getWeather() {
        return weather;
    }

    public void setWeather(List<ForecastItem> weather) {
        this.weather = weather;
    }

    public ForecastWind getWind() {
        return wind;
    }

    public void setWind(ForecastWind wind) {
        this.wind = wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public String toString(int indent) {
        String indentString = "";
        for (int i = 0; i < indent; i++) {
            indentString += "\t";
        }

        String returnval = String.format("\nDate: %s ", getTime());
        returnval += "\n---------------------------------";
        returnval += String.format("\n%s", measurements.toString());
        returnval += String.format("\n%s", weather.get(0).getDescription());
        returnval += String.format("\n%s", wind.toString());
        returnval += "\n---------------------------------\n";

        Log.i("STR", "Return Value: " + returnval);
        return returnval;
    }
}
