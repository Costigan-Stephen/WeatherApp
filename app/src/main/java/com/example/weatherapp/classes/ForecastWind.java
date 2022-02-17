package com.example.weatherapp.classes;

public class ForecastWind {
    public double speed;
    public int deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public String toString() {
        return String.format("Wind Speed: %.1f mph", speed);
    }
}
