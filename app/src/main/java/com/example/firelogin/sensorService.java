package com.example.firelogin;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class sensorService {

    public static final String QUERY_TEMP_SENSOR = "http://192.168.0.24:80/temp";
    public static final String QUERY_GET_HUMIDITY = "http://192.168.0.24:80/humi";


    Context context;
    String sensorTemp;
    String humi;

    public sensorService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String response);
    }

    // UI thread, the request needs time to retrieve data, we must perform the Async Request. Hence, the return will give back NULL
    // If we do not use Callback the program just run through the request without waiting and return NULL

    public void getTemp(VolleyResponseListener volleyResponseListener){
        // Instantiate the RequestQueue.
        String url = QUERY_TEMP_SENSOR;

        // Setup a request for JSON ARRAY
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                sensorTemp = "";
                try {
                    JSONObject JSON_temp = response.getJSONObject(0);
                    sensorTemp = JSON_temp.getString("temp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(sensorTemp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });
        // Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getHumi(VolleyResponseListener volleyResponseListener){

        // Using standard request
        // Instantiate the RequestQueue.
        String url = QUERY_GET_HUMIDITY;

        // Setting up for request JSON OBJECT
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                humi = "";
                humi = response.optString("humi");

                volleyResponseListener.onResponse(humi);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);

    }



}

