package com.example.weatherapp.classes;

public class Weather {
    double temp;
    int pressure;
    int humidity;
    double temp_min;
    double temp_max;

    public Weather(double temp, int pressure, int humidity, double temp_min, double temp_max){
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public double getTemp_max(){
        return temp_max;
    }

    public double getTemp(){
        return temp;
    }

    public String maxtempToString() {
        return String.format("Max Temp: %.1fF", temp_max);
    }

    public String toString() {
        return String.format("Temp: %.1fF", temp);
    }
}
