package com.example.firelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class tempHumidRecords extends AppCompatActivity {

    private LineChart lineChart;
    private BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_humid_records);

        // Back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Set app bar title
        getSupportActionBar().setTitle("Records");

        // Setup line charts
        lineChart = findViewById(R.id.tempChart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDrawBorders(true);


        // Setup bar charts
        barChart = findViewById(R.id.humidChart);
        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setDrawBorders(true);


        // Declare constructor
        final sensorRecords sensorRecords = new sensorRecords(tempHumidRecords.this);

        sensorRecords.getRecords(new sensorRecords.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(tempHumidRecords.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                lineChart.setNoDataText("No Data");
                barChart.setNoDataText("No Data");
            }

            @Override
            public void onResponseRecords(JSONArray response) {
                ArrayList<Entry> tempDataValues = new ArrayList<Entry>();
                ArrayList<BarEntry> humidDataValues= new ArrayList<BarEntry>();
                ArrayList<String> DateValues = new ArrayList<String>();

                for (int i=0; i<response.length();i++) {
                    String time = response.optJSONObject(i).optString("Time");
                    String temp = response.optJSONObject(i).optString("Temp");
                    String humid = response.optJSONObject(i).optString("Humid");

                    String formattedTime = time.substring(0,2) + ":" +time.substring(3,5) + ":" + time.substring(4,6);

                    DateValues.add(formattedTime);
                    tempDataValues.add(new Entry(i, Float.parseFloat(temp)));
                    humidDataValues.add(new BarEntry(i, Float.parseFloat(humid)));

                }

                LineDataSet lineDataSet1 = new LineDataSet(tempDataValues,"Temp Data");
                BarDataSet barDataSet1  = new BarDataSet(humidDataValues, "Humid Data");

                ////////////////////////////////////////////////////////
                ArrayList<ILineDataSet> dataSets_Line = new ArrayList<>();
                dataSets_Line.add(lineDataSet1);

                lineDataSet1.setLineWidth(4);
                lineDataSet1.setColor(Color.RED);


                LineData data_line = new LineData(dataSets_Line);
                lineChart.setData(data_line);
                lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(DateValues));

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setGranularity(2f);

                lineChart.invalidate();




                /////////////////////////////////////////////////////////

                ArrayList<IBarDataSet> dataSets_Bar = new ArrayList<>();
                dataSets_Bar.add(barDataSet1);

                BarData data_bar = new BarData(dataSets_Bar);
                barChart.setData(data_bar);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(DateValues));

                XAxis xAxisBar = barChart.getXAxis();
                xAxisBar.setGranularity(2f);

                barChart.invalidate();




                //Toast.makeText(tempHumidRecords.this, "JSON: " +   response.optJSONObject(0).optString("Time"), Toast.LENGTH_SHORT).show();
            }
        });



    }

}
