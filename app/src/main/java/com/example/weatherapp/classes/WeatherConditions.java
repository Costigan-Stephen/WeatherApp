package com.example.weatherapp.classes;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class WeatherConditions {
    int id;
    String name;
    //Gson main;
    @SerializedName("main")
    Weather measurements ;
    ForecastWind wind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weather getMeasurements() {
        return measurements;
    }

    public double getTempMax() {
        return measurements.getTemp_max();
    }

    public double getWind(){
        return wind.getSpeed();
    }

    public void setMeasurements(Weather measurements) {
        this.measurements = measurements;
    }

    public String toString(int indent) {
        String returnval = "";
        String indentStr = "";
        for (int i = 0; i < indent; i++) {
            indentStr += "\tF";
        }

        returnval += String.format("Current weather for %s :", name);
        returnval += "\n---------------------------------";
        returnval += String.format("\n%s", measurements.toString());
        returnval += String.format("\n%s", measurements.maxtempToString());
        returnval += String.format("\n%s", wind.toString());
        returnval += "\n---------------------------------\n";


        Log.i("STR", "Return Value: " + returnval);
        return returnval;
    }
}
