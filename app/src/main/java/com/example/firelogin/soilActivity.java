package com.example.firelogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class soilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil2);

        // Back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set title App bar
        getSupportActionBar().setTitle("Temperature and Humidity");

        // Define the variable
        final CardView tempCard = findViewById(R.id.tempCard);
        final CardView humiCard = findViewById(R.id.humiCard);
        final sensorService sensorService = new sensorService(soilActivity.this);
        final TextView textViewTemp = (TextView) findViewById(R.id.temperatureShow);
        final TextView textViewHumi = (TextView) findViewById(R.id.humidityShow);
        final Button recordButton = (Button) findViewById(R.id.recordButton);

        // Set initial display sensor values after click to Soil page
        sensorService.getTemp(new sensorService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(soilActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                textViewTemp.setText("N/A");
            }

            @Override
            public void onResponse(String response) {
                textViewTemp.setText(response + "°C");
            }
        });


        sensorService.getHumi(new sensorService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(soilActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                textViewHumi.setText("N/A");
            }

            @Override
            public void onResponse(String response) {
                textViewHumi.setText(response + "%");
            }
        });

        // Set click update temp or humidity
        // Set click on Temperature Card
        tempCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sensorService.getTemp(new sensorService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(soilActivity.this, "Error: " + message , Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String response) {
                        textViewTemp.setText(response + "°C");
                        Toast.makeText(soilActivity.this, "Returned temperature: " + response , Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        // Set click on Humidity Card
        humiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorService.getHumi(new sensorService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(soilActivity.this, "Error: " + message , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        textViewHumi.setText(response + "%");
                        Toast.makeText(soilActivity.this, "Returned humidity: " + response , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // Set Record click
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(soilActivity.this, "Record Clicked: ", Toast.LENGTH_SHORT).show();
                Intent showRecord = new Intent(soilActivity.this,tempHumidRecords.class);
                startActivity(showRecord);
            }
        });



        // refreshContent();
    }

    public void refreshContent(){
        final CardView tempCard = findViewById(R.id.tempCard);
        final TextView textViewTemp = (TextView) findViewById(R.id.temperatureShow);
        final sensorService sensorService = new sensorService(soilActivity.this);

        sensorService.getTemp(new sensorService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(soilActivity.this, "Refresh Error: " + message, Toast.LENGTH_SHORT).show();
                textViewTemp.setText("N/A");
            }

            @Override
            public void onResponse(String response) {
                textViewTemp.setText("Refreshed temp" + response + "°C");
            }
        });

        // After 2s refresh contents
        refresh(2000);

    }

    private void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refreshContent();
            }
        };

        handler.postDelayed(runnable, milliseconds);
    }
}
