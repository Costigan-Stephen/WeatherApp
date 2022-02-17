package com.example.weatherapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.classes.Api;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Api api ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getTemp(View view) {
        getWeather(view, "weather");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getForecast(View view) {
        getWeather(view, "forecast");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getWeather(View view, String type) {
        if( ((TextView) findViewById(R.id.txt_city)).length() > 0) {
            String value = ((EditText) findViewById(R.id.txt_city)).getText().toString();
            String apiString = "bcaeef7f3f256798406a93ab87aa490d";
            try {
                Api api = new Api(value, apiString, type, this);
                Thread thread = new Thread((Runnable) api);
                thread.start();
                thread.join();
                ((TextView) findViewById(R.id.txt_result)).setText(api.getTextOut());

            } catch (IOException | InterruptedException e) {
                Toast toast = Toast.makeText(this, "Error, something went wrong!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "Error, please enter a city!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}