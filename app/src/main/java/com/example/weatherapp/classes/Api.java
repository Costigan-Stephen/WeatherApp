package com.example.weatherapp.classes;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.weatherapp.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Api implements Runnable {
    String url;
    String charset;
    String query;
    String city;
    String type;
    String api;

    public String getTextOut() {
        return textOut;
    }

    String textOut;
    InputStream response;
    Gson gson = new Gson();
    MainActivity main;

    // DELETE LATER https://api.openweathermap.org/data/2.5/weather?q=melbourne&appid=bcaeef7f3f256798406a93ab87aa490d
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Api(String name, String api, String type, MainActivity main) throws IOException {
        this.query = createQuery(name, api, type);
        this.city = name;
        this.type = type;
        this.api = api;
        this.textOut = "";
        this.main = main;
    }

    public WeatherConditions sendWeatherRequest(String url, String query ) throws IOException {
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        WeatherConditions weather = new WeatherConditions();

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        try {
            InputStream responseStream = connection.getInputStream();

            try (Scanner scanner = new Scanner(responseStream)) {
                String response = scanner.useDelimiter("\\A").next();
                //System.out.println(response);
                weather = gson.fromJson(response, WeatherConditions.class);
                /*String weatherGson = gson.toJson(weather);
                System.out.println(weatherGson);*/
            }
        } catch (MalformedURLException e) {
            System.out.println("\nAPI Error");
        } catch (IOException e) {
            System.out.println("\nError establishing connection");
        }
        textOut = weather.toString(0);
        return weather;
    }



    public ForecastWeather sendForecastRequest(String url, String query ) throws IOException {
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        ForecastWeather weather = new ForecastWeather();

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        try {

            InputStream responseStream = connection.getInputStream();

            try (Scanner scanner = new Scanner(responseStream)) {
                String response = scanner.useDelimiter("\\A").next();
                weather = gson.fromJson(response, ForecastWeather.class);
            }
        } catch (MalformedURLException e) {
            System.out.println("\nAPI Error");
        } catch (IOException e) {
            System.out.println("\nError establishing connection");
        }
        return weather;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCityList(String api) throws IOException {
        String cities[] = {"Adelaide", "Melbourne", "Sydney", "Perth", "Darwin"};
        List<WeatherConditions> cityList = new ArrayList();
        WeatherConditions item = new WeatherConditions();
        ForecastWeather forecast = new ForecastWeather();
        String q = "";
        for(String city: cities){
            q = createQuery(city, api, "weather");
            item = sendWeatherRequest(url, q);
            cityList.add(item);
        }
        String out = "";
                // Sort by max temp
        List<WeatherConditions> sortedList = cityList.stream()
                .sorted(Comparator.comparing(WeatherConditions::getTempMax))
                .collect(Collectors.toList());
        Collections.reverse(sortedList);
        //System.out.println("Arranging by Max Temp:");
        out += "Arranging by Max Temp:\n";
        for(WeatherConditions location: sortedList){
            location.toString(0);
            q = createQuery(location.getName(), api, "forecast");
            forecast = sendForecastRequest(url, q);
            //System.out.println(forecast.toString(0));
            out += forecast.toString(0);
        }

        // sort by wind speed
        sortedList = cityList.stream()
                .sorted(Comparator.comparing(WeatherConditions::getWind))
                .collect(Collectors.toList());
        Collections.reverse(sortedList);
        //System.out.println("Arranging by wind speed:");
        out += "Arranging by wind speed:";
        for(WeatherConditions location: sortedList){
            location.toString(0);
        }
        return out;
    }

    public String createQuery(String name, String api, String type){
        this.url = "https://api.openweathermap.org/data/2.5/"+type;
        this.charset = UTF_8.name();
        String query = ""; // local variable
        try {
            query = String.format("q=%s&appid=%s&units=imperial",
                    URLEncoder.encode(name, charset),
                    URLEncoder.encode(api, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return query;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        String reference = "";
        Log.i("INFO", "About to create intent with city: " + city);

        if(city == "getListCities") {
            try {
                reference = getCityList(api).toString();
            } catch (IOException e) {
                Log.i("ERR", "Error getting city list");
            }
        }
        else if(type == "weather") {
            try {
                WeatherConditions weather = sendWeatherRequest(url, query);
                reference = weather.toString(0);
            } catch (IOException e) {
                Log.i("ERR", "Unable to handle weather request");
            }

        }
        else {
            ForecastWeather weather = null;
            try {
                weather = sendForecastRequest(url, query);
                reference = weather.toString(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        textOut = reference;
    }
}
